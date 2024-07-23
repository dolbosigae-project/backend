package com.gae.controller;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    //비밀번호 찾기
    @PostMapping("/find/passwd")
    public ResponseEntity<String> findPasswd(@RequestBody Map<String, String> params) {
        String id = params.get("id");
        String passwd = params.get("passwd");
        System.out.println("ID: " + id);
        System.out.println("Password: " + passwd);

        int updateResult = memberService.updatePasswd(params);
        if (updateResult > 0) {
            return ResponseEntity.ok("회원 정보가 업데이트되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 업데이트에 실패했습니다.");
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}