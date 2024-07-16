package com.gae.controller;

import java.lang.reflect.Member;
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
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest, HttpSession session) {
        try {
            String id = loginRequest.get("id");
            String pass = loginRequest.get("pass");
            BoardMemberDTO dto = memberService.login(id, pass);
            if (dto != null) {
                session.setAttribute("user", dto);
                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error: " + e.getMessage());
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
    
    @GetMapping("/member/search")
    public ResponseEntity<?> memberSearch(@RequestParam String category, @RequestParam String term) {
        List<Member> searchResults = memberService.searchMembers(category, term);
        return ResponseEntity.ok(searchResults);
    }
    
    @GetMapping("/member/duplicate")
    @ResponseBody
    public int isDuplicate(@RequestParam String idValue) {
    	System.out.println(idValue);
        return memberService.checkDuplicate(idValue);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}