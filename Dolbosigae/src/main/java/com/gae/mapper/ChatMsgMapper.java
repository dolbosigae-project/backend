package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.BoardMemberDTO;
import com.gae.dto.ChatMsgDTO;
import com.gae.dto.NtDTO;

@Mapper
public interface ChatMsgMapper {
	
	List<BoardMemberDTO> searchChatMembers(@Param("category") String category, @Param("search") String search);
	
//	void insertMsg(ChatMsgDTO dto);
//    void insertRMsg(ChatMsgDTO dto);
//    ChatMsgDTO selectMsgById(String msgNo);
//    List<ChatMsgDTO> selectMsgsBySender(String id);
//    List<ChatMsgDTO> selectRMsgsByReciver(String id);
//	int selectNextMsgNo();
	
	int selectNextMsgId();
    void insertMsg(ChatMsgDTO message);
//    void insertNotification(NtDTO notification);
    ChatMsgDTO selectMsgById(int msgId);
    List<ChatMsgDTO> selectMsgsBySender(String sId);
    List<ChatMsgDTO> selectMsgsByReceiver(String rId);
    List<NtDTO> getNotificationsByUserId(String rId);
    void markNotificationAsSeen(int notifId);
    List<String> getAllUserIds();
	void deleteMsgById(int msgId);
	void updateMsgReadStatus(@Param("msgId") int msgId, @Param("rId") String rId);
	
}