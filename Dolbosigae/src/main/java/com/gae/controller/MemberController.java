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

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;

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
        System.out.println(member);
        int result = memberService.updateMember(member);
        System.out.println(result);
        
        return ResponseEntity.ok("회원 정보가 업데이트되었습니다.");
    }
    
    //카테고리별로 멤버 서치
    @GetMapping("/member/search")
    public ResponseEntity<?> memberSearch(@RequestParam String category, @RequestParam String term) {
        List<Member> searchResults = memberService.searchMembers(category, term);
        return ResponseEntity.ok(searchResults);
    }
    
    //마이페이지 조회시 사용
    @GetMapping("/member/search/{id}")
    public ResponseEntity<?> memberIdSearch(@PathVariable String id) {
        return memberSearch("회원ID", id);
    }
    
    //아이디 중복 찾기
    @GetMapping("/member/duplicate")
    @ResponseBody
    public int isDuplicate(@RequestParam String idValue) {
        return memberService.checkDuplicate(idValue);
    }
    
    @PostMapping("/member/register")
    public ResponseEntity<String> registerMember(@RequestBody BoardMemberDTO member) {
        System.out.println(member);
        memberService.registerMember(member);
        return ResponseEntity.ok("회원가입 성공");
    }
    
    
//    @GetMapping("/member/list")
//    @ResponseBody
//    public ResponseEntity<MemberResponseVo> selectAllMember(@RequestParam(defaultValue = "1") int page) {
//        return ResponseEntity.ok(memberService.getMemberList(page));
//    }
    
    @GetMapping("/member/walkmates")
    @ResponseBody
    public ResponseEntity<MemberResponseVo> selectWalkMates(@RequestParam(defaultValue = "1") int page) {
        logger.debug("산책 친구 목록 요청 수신: page={}", page);
        MemberResponseVo response = memberService.getWalkMateList(page);
        logger.debug("응답: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/member/walkmates/search")
    @ResponseBody
    public ResponseEntity<MemberResponseVo> searchWalkMatesByRegion(@RequestParam String region, @RequestParam(defaultValue = "1") int page) {
        logger.debug("지역별 산책 친구 검색 요청 수신: region={}, page={}", region, page);
        MemberResponseVo response = memberService.searchWalkMatesByRegion(region, page);
        logger.debug("응답: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/member/petprofile/{id}")
    public ResponseEntity<?> selectPetProfile(@PathVariable String id) {
        BoardMemberDTO member = memberService.getPetProfile(id);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("찾을 수 없는 회원");
        }
    }

    @PostMapping("/mate/fav")
    public void insertFavorite(@RequestParam String loginId, @RequestParam String targetId) {
        memberService.insertFavorite(loginId, targetId);
    }
    
    
    
  //채팅창 페이지 왼쪽에 띄울 반려동물의 정보 가져오기
    //가 아니고 일단 사용자의 정보를 가져옴
//    @GetMapping("/member/myInfo")
//    public ResponseEntity<BoardMemberDTO> selectLoginUserInfo(HttpSession session) {
//        BoardMemberDTO user = (BoardMemberDTO) session.getAttribute("user");
//        if (user == null) 
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        
//        String id = user.getBoardMemberId();
//        BoardMemberDTO userprofile = memberService.selectLoginUserInfo(id);
//        if (userprofile != null) {
//            System.out.println("현재 ID : " + id);
//            return ResponseEntity.ok(userprofile);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//    
//    @GetMapping("/member/petinfo")
//    public ResponseEntity<BoardMemberDTO> selectPetInfo(HttpSession session) {
//        BoardMemberDTO user = (BoardMemberDTO) session.getAttribute("user");
//        if (user == null) 
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        
//        String id = user.getBoardMemberId();
//        BoardMemberDTO petprofile = memberService.selectPetInfo(id);
//        if (petprofile != null) {
//            System.out.println("현재 ID : " + id);
//            return ResponseEntity.ok(petprofile);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    } 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}