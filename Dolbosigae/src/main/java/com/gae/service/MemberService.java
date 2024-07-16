package com.gae.service;

import java.lang.reflect.Member;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        if (memberResult == 0) {
            throw new RuntimeException("Failed to delete member");
        }
        if(petResult == 0) {
        	throw new RuntimeException("Failed to delete member");
        }
		return 0;
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

    public int checkDuplicate(String idValue) {
        Integer result = memberMapper.checkDuplicate(idValue);
        return result != null ? result : 0;
    }

    @Transactional
	public void registerMember(BoardMemberDTO member) {
		memberMapper.insertMember(member);
		memberMapper.insertPet(member);
	}
    
    
}