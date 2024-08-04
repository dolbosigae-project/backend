package com.gae.controller;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gae.dto.BoardMemberDTO;
import com.gae.service.MemberService;
import com.gae.vo.MemberResponseVo;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "https://dolbosigae.site")
@Controller
public class MemberController {
    private final MemberService memberService;
    
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);


    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest, HttpSession session) {
        try {
            String id = loginRequest.get("id");
            String pass = loginRequest.get("pass");
            BoardMemberDTO dto = memberService.login(id, pass);
            //System.out.println(dto);
            if (dto != null) {
                session.setAttribute("user", dto);
                // 사용자 정보를 포함한 응답 생성
                Map<String, Object> response = new HashMap<String, Object>();
                response.put("success", true);
                response.put("user", dto); // 사용자 정보를 응답에 포함
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public ResponseEntity<String> logout(HttpSession session) {
        try {
            session.invalidate();
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error: " + e.getMessage());
        }
    }
    
    //비밀번호 찾기
    @PostMapping("/find/passwd")
    public ResponseEntity<String> findPasswd(@RequestBody Map<String, String> params) {
        String id = params.get("id");
        String passwd = params.get("passwd");
        //System.out.println("ID: " + id);
        //System.out.println("Password: " + passwd);

        int updateResult = memberService.updatePasswd(params);
        if (updateResult > 0) {
            return ResponseEntity.ok("회원 정보가 업데이트되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 업데이트에 실패했습니다.");
        }
    }
    
    //비밀번호 찾기 - 아이디와 회원 이름 일치여부 판별
    @PostMapping("/member/check/id")
    @ResponseBody
    public int checkId(@RequestBody Map<String, String> params) {
        String idValue = params.get("idValue");
        String nameValue = params.get("nameValue");
        //System.out.println("idValue: " + idValue);
        //System.out.println("nameValue: " + nameValue);

        int result = memberService.checkId(params);
        
        return result;
    }
    
    //아이디 중복 찾기
    @GetMapping("/member/duplicate")
    @ResponseBody
    public int isDuplicate(@RequestParam String idValue) {
    	return memberService.checkDuplicate(idValue);
    }
    

    @GetMapping("/member/list")
    @ResponseBody
    public ResponseEntity<MemberResponseVo> selectAllMember(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(memberService.getMemberList(page));
    }

    @DeleteMapping("/member/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> memberDelete(@PathVariable String id) {
        try {
            int result = memberService.deleteMember(id);
            if(result != 0) {
                return ResponseEntity.ok("회원정보 삭제 성공");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원정보 삭제 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생: " + e.getMessage());
        }
    }
    
    @PostMapping("/member/update")
    public ResponseEntity<String> memberUpdate(@RequestBody BoardMemberDTO member) {
        try {
            System.out.println("Received Member Data: " + member); // 데이터 출력
            int result = memberService.updateMember(member);
            System.out.println("Update Result: " + result);

            return ResponseEntity.ok("회원 정보가 업데이트되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원정보 업데이트 중 오류가 발생했습니다.");
        }
    }
    
    //카테고리별로 멤버 서치
    @GetMapping("/member/search")
    public ResponseEntity<?> memberSearch(@RequestParam String category, @RequestParam String term) {
        List<Member> searchResults = memberService.searchMembers(category, term);
        return ResponseEntity.ok(searchResults);
    }
    
    //마이페이지 조회
    @GetMapping("/member/search/{id}")
    public ResponseEntity<?> findMemberById(@PathVariable String id) {
        BoardMemberDTO member = memberService.myPage(id);
        if (member != null) {
        	System.out.println("Member found: " + member); // 디버깅을 위해 추가
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보를 찾을 수 없습니다.");
        }
    }
    
    @PostMapping("/member/register")
    public ResponseEntity<String> registerMember(@RequestBody BoardMemberDTO member) {
        System.out.println(member);
        memberService.registerMember(member);
        return ResponseEntity.ok("회원가입 성공");
    }
    
    
    //이 밑으로 주의
    @GetMapping("/member/walkmates")
    @ResponseBody
    public ResponseEntity<MemberResponseVo> selectWalkMates(@RequestParam(defaultValue = "1") int page) {
        logger.debug("산책 친구 목록 요청 수신: page={}", page);
        MemberResponseVo response = memberService.getWalkMateList(page);
        logger.debug("응답: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/member/walkmate/search")
    @ResponseBody
    public ResponseEntity<MemberResponseVo> searchWalkMateAddress(@RequestParam String addressText) {
        try {
            logger.debug("주소로 회원 검색 요청 수신: addressText={}", addressText);
            // 페이지네이션 없이 검색된 결과를 반환하는 메서드 호출
            MemberResponseVo response = memberService.searchWalkMateAddress(addressText);
            logger.debug("응답: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("주소로 회원 검색 중 오류 발생: addressText={}, error={}", addressText, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MemberResponseVo(null, null));
        }
    }

    
    @GetMapping("/member/petprofile/{id}")
    public ResponseEntity<?> selectPetProfile(@PathVariable String id) {
        logger.debug("Pet profile 요청 수신: id={}", id);
        try {
            BoardMemberDTO member = memberService.getPetProfile(id);
            if (member == null) {
                logger.debug("Pet profile 찾을 수 없음: id={}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("찾을 수 없는 회원");
            }
            logger.debug("Pet profile 가져오기 성공: {}", member);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            logger.error("Pet profile 가져오는 중 오류 발생: id={}, error={}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    
    //즐찾 추가삭제
    @PostMapping("/mate/fav")
    public ResponseEntity<?> btnFavorite(@RequestBody Map<String, String> params) {
        String loginId = params.get("loginId");
        String targetId = params.get("targetId");
        
        if (loginId == null || targetId == null) {
            return ResponseEntity.badRequest().body("필요한 매개변수를 찾지못함");
        }
        
        try {
            boolean isFavorite = memberService.btnFavorite(loginId, targetId);
            if (isFavorite) {
                return ResponseEntity.ok("즐겨찾기에 추가되었습니다.");
            } else {
                return ResponseEntity.ok("즐겨찾기가 삭제되었습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("즐겨찾기 상태 변경 중 오류가 발생했습니다.");
        }
    }
    
    //즐찾 추가상태를 확인하기 위한 
    @GetMapping("/mate/fav/status")
    public ResponseEntity<?> getFavoriteStatus(@RequestParam String loginId, @RequestParam String targetId) {
        if (loginId == null || targetId == null) {
            return ResponseEntity.badRequest().body("필요한 매개변수를 찾지못함");
        }
        
        try {
            boolean isFavorite = memberService.isFavorite(loginId, targetId);
            return ResponseEntity.ok(Map.of("isFavorite", isFavorite));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("즐겨찾기 상태 확인 중 오류가 발생했습니다.");
        }
    }
    
    //관리자 전용 - 게시판에 프로필 노출 끄기
    @PostMapping("/walkmate/changeTF")
    public ResponseEntity<String> changeWalkTF(@RequestBody Map<String, List<String>> params) {
        List<String> Wid = params.get("Wid");
        System.out.println("changeWalkTF endpoint called with Wid: " + Wid);
        try {
            memberService.changeWalkTF(Wid);
            return ResponseEntity.ok("프로필이 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("프로필 변경 중 오류가 발생했습니다.");
        }
    }
    
    //즐겨찾기 리스트 확인
    @GetMapping("/mate/fav/list")
    public ResponseEntity<?> mateFavList(@RequestParam String id){
    	return ResponseEntity.ok(memberService.getMateFavList(id));
    }
    
    
    
    
}