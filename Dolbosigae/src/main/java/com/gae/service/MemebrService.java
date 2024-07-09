package com.gae.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gae.dto.BoardMemberDTO;
import com.gae.mapper.MemberMapper;

@Service
public class MemebrService {
	private MemberMapper memberMapper;

	public MemebrService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	public BoardMemberDTO login(String id, String passwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pass", passwd);
		
		return memberMapper.login(map);
		
	}
	
	
	
}
