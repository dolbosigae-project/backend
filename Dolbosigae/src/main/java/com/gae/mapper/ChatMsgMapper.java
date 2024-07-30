package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.BoardMemberDTO;
import com.gae.dto.ChatMsgDTO;

@Mapper
public interface ChatMsgMapper {
	
	List<BoardMemberDTO> searchChatMembers(@Param("category") String category, @Param("search") String search);
	
	void insertMsg(ChatMsgDTO dto);
    void insertRMsg(ChatMsgDTO dto);
    ChatMsgDTO selectMsgById(String msgNo);
    List<ChatMsgDTO> selectMsgsBySender(String id);
    List<ChatMsgDTO> selectRMsgsByReciver(String id);
}