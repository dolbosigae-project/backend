package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.BoardMemberDTO;

@Mapper
public interface MemberMapper {

	BoardMemberDTO login(@Param("id") String id, @Param("pass") String pass);
	int getTotalCount();
	List<BoardMemberDTO> getMemberList(@Param("startRow") int startRow, @Param("endRow") int endRow);
	int deleteMember(@Param("id") String id);
}
