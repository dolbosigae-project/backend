package com.gae.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Member;



import com.gae.controller.MemberController;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gae.dto.BoardMemberDTO;
import com.gae.mapper.MemberMapper;
import com.gae.vo.MemberPaggingVo;
import com.gae.vo.MemberResponseVo;

@Service
public class MemberService {
    @Autowired
    private MemberMapper memberMapper;
    
    @Autowired
    private S3ImageService s3ImageService;
    
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    public MemberService(MemberMapper memberMapper, S3ImageService s3ImageService) {
        this.memberMapper = memberMapper;
        this.s3ImageService = s3ImageService;
    }

    //로그인
    public BoardMemberDTO login(String id, String passwd) {
        try {
            return memberMapper.login(id, passwd);
        } catch (Exception e) {
            System.err.println("로그인 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public MemberResponseVo getMemberList(int page) {
        int pageOfContentCount = 10; // 페이지당 멤버 수
        int totalCount = memberMapper.getTotalCount(); // 전체 멤버 수 가져오기
        MemberPaggingVo paggingVo = new MemberPaggingVo(totalCount, page, pageOfContentCount);

        int startRow = (page - 1) * pageOfContentCount;
        int endRow = startRow + pageOfContentCount;
        List<BoardMemberDTO> members = memberMapper.getMemberList(startRow, endRow);
        return new MemberResponseVo(members, paggingVo);
    }

    @Transactional
    public int deleteMember(String id) {
        int memberResult = memberMapper.deleteMember(id);
        if (memberResult == 0) {
            throw new RuntimeException("Failed to delete member");
        }
        return 1; // 성공적으로 삭제된 경우 1 반환
    }

    @Transactional
    public int updateMember(BoardMemberDTO member) {
        int memberResult = memberMapper.updateMember(member);
        int petResult = memberMapper.updatePet(member);
        
        if (member.getPasswordChanged() == 1) {  
            int passwordResult = memberMapper.updateMemberPassword(member);
            if (passwordResult == 0) {
                throw new RuntimeException("Failed to update member password");
            }
        }

        if (memberResult == 0) {
            throw new RuntimeException("Failed to update member");
        }
        if (petResult == 0) {
            throw new RuntimeException("Failed to update pet");
        }
        return memberResult;
    }

    public List<Member> searchMembers(String category, String term) {
        switch (category) {
            case "회원ID":
                return memberMapper.searchByBoardMemberId(term);
            case "회원이름":
                return memberMapper.searchByBoardMemberName(term);
            case "지역":
                return memberMapper.searchByBoardMemberRegion(term);
            case "등급":
                return memberMapper.searchByBoardMemberGradeName(term);
            default:
                throw new IllegalArgumentException("Invalid search category: " + category);
        }
    }
    
    
	public BoardMemberDTO myPage(String id) {
		return memberMapper.myPage(id);
	}
    
    public int checkDuplicate(String idValue) {
        Integer result = memberMapper.checkDuplicate(idValue);
        return result != null ? result : 0;
    }
    
	//비밀번호 찾기 - 아이디와 회원 이름 일치여부 판별
    public int checkId(Map<String, String> params) {
        String idValue = params.get("idValue");
        String nameValue = params.get("nameValue");
        int nameResult = 0;
        int result = 0;
        
        Integer duplicateResult = memberMapper.checkDuplicate(idValue);
        if (duplicateResult == null) {
            duplicateResult = 0;
        }
        
        String nameMatch = nameCheck(nameValue);
        //System.out.println(nameMatch);
        //System.out.println(idValue);
        
        if (nameMatch != null) {
            //System.out.println(nameMatch.equals(idValue));
            if (nameMatch.equals(idValue)) {
                nameResult = 1;
            }
        }
        
        if (duplicateResult != 0 && nameResult != 0) {
            result = 1;
        }
        
        return result;
    }
	
	//비밀번호 찾기 - 이름 일치 확인
	public String nameCheck(String name) {
		String resultId = memberMapper.checkName(name);
		//System.out.println(resultId);
		return resultId;
	}

	@Transactional
	public void registerMember(BoardMemberDTO member) {
	    try {
	        // 프로필 이미지가 있는 경우 처리
	        if (member.getProfileImg() != null && !member.getProfileImg().isEmpty()) {
	            logger.info("프로필 이미지 처리 중...");

	            String base64Image = member.getProfileImg().split(",")[1]; // "data:image/png;base64," 부분 제거
	            String mimeType = member.getProfileImg().split(",")[0].split(":")[1].split(";")[0]; // MIME 타입 추출
	            String extension;
	            switch (mimeType) {
	                case "image/jpeg":
	                    extension = "jpg";
	                    break;
	                case "image/png":
	                    extension = "png";
	                    break;
	                default:
	                    throw new IllegalArgumentException("Unsupported image type: " + mimeType);
	            }
	            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

	            logger.info("S3에 이미지 업로드 중...");
	            // S3에 업로드
	            String s3ImagePath = s3ImageService.uploadImageBytes(imageBytes, mimeType, extension);
	            logger.info("이미지 업로드 완료. 경로: " + s3ImagePath);

	            member.setPetImagePath(s3ImagePath); // 이미지 경로 설정
	        }

	        memberMapper.insertMember(member);
	        if ("T".equals(String.valueOf(member.getBoardMemberPetWith()))) {
	            memberMapper.insertPet(member);
	            memberMapper.insertPetImg(member);
	        } else {
	            // 반려동물이 없는 경우 클라이언트 단의 닉네임 값을 사용하여 기본값으로 PET 테이블에 데이터를 삽입
	            BoardMemberDTO defaultPet = new BoardMemberDTO();
	            defaultPet.setBoardMemberId(member.getBoardMemberId());
	            defaultPet.setBoardMemberNick(member.getBoardMemberNick()); // 클라이언트 단에서 전달된 닉네임 값 사용
	            memberMapper.insertDefaultPet(defaultPet);
	        }
	    } catch (Exception e) {
	        logger.error("회원 등록 중 오류 발생: " + e.getMessage(), e);
	        throw new RuntimeException("회원 등록 중 오류가 발생했습니다.");
	    }
	}
	   

    public int updatePasswd(Map<String, String> params) {
        String boardMemberId = params.get("id");
        String boardMemberPasswd = params.get("passwd");

        return memberMapper.updatePasswd(boardMemberId, boardMemberPasswd);
    }

    
    //이 밑으로 주의
    public MemberResponseVo getWalkMateList(int page) {
        int pageOfContentCount = 7; // 페이지마다 표시될 회원 수
        int totalCount = memberMapper.getTotalCountWalk(); // 펫 프로필을 킨 회원들의 전체 수
        MemberPaggingVo paggingVo = new MemberPaggingVo(totalCount, page, pageOfContentCount);
        
        int startRow = (page - 1) * pageOfContentCount + 1; // 시작 행 (1부터 시작)
        int endRow = startRow + pageOfContentCount - 1; // 종료 행

        logger.debug("산책 친구 목록을 불러오는 중: startRow={}, endRow={}", startRow, endRow);

        List<BoardMemberDTO> members = memberMapper.getWalkMateList(startRow, endRow);
        
        // 콘솔에 결과 출력
//        logger.debug("=== 산책 친구 목록 ===");
        for (BoardMemberDTO member : members) {
            logger.debug(member.toString());
        }

        return new MemberResponseVo(members, paggingVo);
    }

    public MemberResponseVo searchWalkMatesByRegion(String region, int page) {
        int pageOfContentCount = 7; // 페이지마다 표시될 회원 수
        int totalCount = memberMapper.getTotalCountWalkByRegion(region); // 특정 지역의 펫 프로필을 킨 회원들의 전체 수
        MemberPaggingVo paggingVo = new MemberPaggingVo(totalCount, page, pageOfContentCount);
        
        int startRow = (page - 1) * pageOfContentCount + 1; // 시작 행 (1부터 시작)
        int endRow = startRow + pageOfContentCount - 1; // 종료 행

        logger.debug("지역으로 산책 친구 검색: region={}, startRow={}, endRow={}", region, startRow, endRow);

        List<BoardMemberDTO> members = memberMapper.searchWalkMatesByRegion(region, startRow, endRow);
        
        // 콘솔에 결과 출력
        logger.debug("=== 지역별 산책 친구 목록: {} ===", region);
        for (BoardMemberDTO member : members) {
            logger.debug(member.toString());
        }

        return new MemberResponseVo(members, paggingVo);
    }

    public BoardMemberDTO getPetProfile(String id) {
        List<BoardMemberDTO> petProfiles = memberMapper.getPetProfile(id);
        logger.info("Fetched pet profiles for ID {}: {}", id, petProfiles);

        if (petProfiles.isEmpty()) {
            logger.info("No pet profile found for ID {}", id);
            return null;
        }
        return petProfiles.get(0); // 첫 번째 결과만 반환
    }
    
	
	public void insertFavorite(String loginId, String targetId) {
		logger.info("즐겨찾기 등록 시도 - 로그인 ID: {}, 대상 ID: {}", loginId, targetId);
        if (!loginId.equals(targetId)) {
            memberMapper.insertFavorite(loginId, targetId);
            logger.info("즐겨찾기 등록 성공 - 로그인 ID: {}, 대상 ID: {}", loginId, targetId);
        } else {
        	logger.error("자기 자신을 즐겨찾기에 등록 시도 - 로그인 ID: {}", loginId);
        	throw new IllegalArgumentException("자기 자신은 즐겨찾을 수 없습니다");
        }
    }

    
}