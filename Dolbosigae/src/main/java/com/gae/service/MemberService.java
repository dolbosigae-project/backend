package com.gae.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Member;



import com.gae.controller.MemberController;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;
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

    public MemberResponseVo searchWalkMateAddress(String addressText) {
        // 페이지네이션 없이 모든 결과를 가져옵니다.
        List<BoardMemberDTO> members = memberMapper.searchWalkMateAddress(addressText);

        // 콘솔에 결과 출력
        logger.debug("=== 주소별 회원 목록: {} ===", addressText);
        for (BoardMemberDTO member : members) {
            logger.debug(member.toString());
        }

        // 페이지네이션 정보를 null로 설정합니다.
        return new MemberResponseVo(members, null);
    }


    
    public BoardMemberDTO getPetProfile(String id) {
        logger.info("펫 프로필 조회 시도: ID={}", id);
        List<BoardMemberDTO> petProfiles = memberMapper.getPetProfile(id);

        if (petProfiles == null) {
            logger.error("펫 프로필 조회 실패: ID={}에 대한 쿼리가 null을 반환했습니다.", id);
            return null;
        }

        if (petProfiles.isEmpty()) {
            logger.info("펫 프로필 없음: ID={}에 대한 펫 프로필이 존재하지 않습니다.", id);
            return null;
        }

        BoardMemberDTO petProfile = petProfiles.get(0);
        logger.info("펫 프로필 조회 성공: ID={}, 프로필={}", id, petProfile);
        return petProfile;
    }

    
	
//	public void insertFavorite(String loginId, String targetId) {
//		logger.info("즐겨찾기 등록 시도 - 로그인 ID: {}, 대상 ID: {}", loginId, targetId);
//        if (!loginId.equals(targetId)) {
//            memberMapper.insertFavorite(loginId, targetId);
//            logger.info("즐겨찾기 등록 성공 - 로그인 ID: {}, 대상 ID: {}", loginId, targetId);
//        } else {
//        	logger.error("자기 자신을 즐겨찾기에 등록 시도 - 로그인 ID: {}", loginId);
//        	throw new IllegalArgumentException("자기 자신은 즐겨찾을 수 없습니다");
//        }
//    }
    
    public boolean btnFavorite(String loginId, String targetId) {
        logger.info("즐겨찾기 상태 변경 시도 - 로그인 ID: {}, 대상 ID: {}", loginId, targetId);

        try {
            String existingFavorites = memberMapper.getFavorites(loginId);

            if (existingFavorites != null) {
                // 기존 즐겨찾기 목록이 있을 경우
                List<String> favoriteList = new ArrayList<>(Arrays.asList(existingFavorites.split(",")));
                if (favoriteList.contains(targetId)) {
                    // 이미 즐겨찾기에 있는 경우 삭제
                    favoriteList.remove(targetId);
                    String newFavorites = String.join(",", favoriteList);
                    memberMapper.updateFavorites(loginId, newFavorites);
                    logger.info("즐겨찾기 삭제 성공 - 로그인 ID: {}, 대상 ID: {}", loginId, targetId);
                    return false;
                } else {
                    // 즐겨찾기에 없는 경우 추가
                    String newFavorites = existingFavorites + "," + targetId;
                    memberMapper.updateFavorites(loginId, newFavorites);
                    logger.info("즐겨찾기 추가 성공 - 로그인 ID: {}, 대상 ID: {}", loginId, targetId);
                    return true;
                }
            } else {
                // 즐겨찾기 목록이 없을 경우 새로 추가
                memberMapper.insertFavorite(loginId, targetId);
                logger.info("즐겨찾기 등록 성공 - 로그인 ID: {}, 대상 ID: {}", loginId, targetId);
                return true;
            }
        } catch (Exception e) {
            logger.error("즐겨찾기 상태 변경 중 오류 발생", e);
            throw e;
        }
    }

    public boolean isFavorite(String loginId, String targetId) {
        logger.info("즐겨찾기 상태 확인 시도 - 로그인 ID: {}, 대상 ID: {}", loginId, targetId);
        Integer result = memberMapper.isFavorite(loginId, targetId);
        return result != null && result == 1;
    }
    
    public void changeWalkTF(List<String> Wid) {
        System.out.println("changeWalkTF called with Wid: " + Wid); // 로그 추가
        List<String> pIds = memberMapper.selectPidsByMids(Wid);
        System.out.println("PIDs corresponding to WIDs: " + pIds); // 로그 추가
        memberMapper.updateWalkTF(pIds);
    }

    //즐겨찾기 리스트 확인
    public List<String> getMateFavList(String id) {
        String mateFavString = memberMapper.getMateFavList(id);
        
        if (mateFavString != null && !mateFavString.isEmpty()) {
            return Arrays.asList(mateFavString.split(","));
        } else {
            return new ArrayList<>();
        }
    }


    
}