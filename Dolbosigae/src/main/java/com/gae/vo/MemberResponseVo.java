package com.gae.vo;

import java.util.List;

import com.gae.dto.BoardMemberDTO;

public class MemberResponseVo {
    private List<BoardMemberDTO> members;
    private MemberPaggingVo pagination;

    public MemberResponseVo(List<BoardMemberDTO> members, MemberPaggingVo pagination) {
        this.members = members;
        this.pagination = pagination;
    }

    public List<BoardMemberDTO> getMembers() {
        return members;
    }

    public MemberPaggingVo getPagination() {
        return pagination;
    }
}
