package com.gae.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Base64;


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

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
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

    @Transactional
    public void registerMember(BoardMemberDTO member) {
        try {
            // 프로필 이미지가 있는 경우 처리
            if (member.getProfileImg() != null && !member.getProfileImg().isEmpty()) {
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
                // 파일 저장 경로 설정
                String imagePath = "C:\\Users\\user1\\git\\fileupload\\profile\\" + UUID.randomUUID().toString() + "." + extension;

                // 디버깅 메시지 추가
                System.out.println("Saving image to: " + imagePath);

                // 디렉토리가 존재하지 않으면 생성
                File directory = new File("C:\\Users\\user1\\git\\fileupload\\profile");
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                try (OutputStream os = new FileOutputStream(imagePath)) {
                    os.write(imageBytes);
                }
                member.setPetImagePath(imagePath); // 이미지 경로 설정
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
            System.err.println("회원 등록 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("회원 등록 중 오류가 발생했습니다.");
        }
    }

    public int updatePasswd(Map<String, String> params) {
        String boardMemberId = params.get("id");
        String boardMemberPasswd = params.get("passwd");

        return memberMapper.updatePasswd(boardMemberId, boardMemberPasswd);
    }





    
    
}