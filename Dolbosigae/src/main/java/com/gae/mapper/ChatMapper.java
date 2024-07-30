package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.BoardMemberDTO;

@Mapper
public interface ChatMapper {
	
	List<BoardMemberDTO> searchChatMembers(@Param("category") String category, @Param("search") String search);
	
	
}
