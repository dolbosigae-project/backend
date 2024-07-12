package com.gae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gae.dto.BoardMemberDTO;
import com.gae.mapper.MemberMapper;

@Service
public class MemberService {
	@Autowired
    private MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public BoardMemberDTO login(String id, String passwd) {
        try {
            return memberMapper.login(id, passwd);
        } catch (Exception e) {
            System.err.println("로그인 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
