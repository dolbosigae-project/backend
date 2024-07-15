package com.gae.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
