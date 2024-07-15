package com.gae.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.gae.dto.BoardMemberDTO;
import com.gae.service.MemberService;
import com.gae.vo.MemberPaggingVo;
import com.gae.vo.MemberResponseVo;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody Map<String, String> loginRequest, HttpSession session) {
        try {
            String id = loginRequest.get("id");
            String pass = loginRequest.get("pass");
            BoardMemberDTO dto = memberService.login(id, pass);
            if (dto != null) {
                session.setAttribute("user", dto);
                return "success";
            } else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
    }
    
    @GetMapping("/logout")
    @ResponseBody
    public String logout(HttpSession session) {
        try {
            session.invalidate();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
    }
    
    @GetMapping("/member/list")
    @ResponseBody
    public MemberResponseVo selectAllMember(@RequestParam(defaultValue = "1") int page) {
        return memberService.getMemberList(page);
    }
}
