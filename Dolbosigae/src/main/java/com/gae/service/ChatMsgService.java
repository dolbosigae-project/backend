package com.gae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gae.dto.BoardMemberDTO;
import com.gae.dto.ChatMsgDTO;
import com.gae.mapper.ChatMsgMapper;

@Service
public class ChatMsgService {
    private final Map<String, Set<String>> chatRooms = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
    
    @Autowired
    private ChatMsgMapper msgMapper;
    
    // 채팅방 생성
    public synchronized boolean createRoom(String roomId, String userA, String userB) {
        if (chatRooms.containsKey(roomId)) {
            return false; // 방이 이미 존재함
        }

        Set<String> users = ConcurrentHashMap.newKeySet();
        users.add(userA);
        users.add(userB);
        chatRooms.put(roomId, users);

        return true;
    }

    // 방에 회원 추가
    public synchronized boolean addUserToRoom(String roomId, String username) {
        Set<String> users = chatRooms.get(roomId);
        if (users == null) {
            return false; // 방이 존재하지 않음
        }

        if (users.size() >= 2) {
            return false; // 방이 꽉 찼음
        }

        return users.add(username);
    }

    // 방에서 회원 지움 
    public synchronized void removeUserFromRoom(String roomId, String username) {
        Set<String> users = chatRooms.get(roomId);
        if (users != null) {
            users.remove(username);
            if (users.isEmpty()) {
                chatRooms.remove(roomId); // 사용자가 모두 나가면 채팅방 삭제
            }
        }
    }

    // 채팅방 삭제
    public synchronized void removeChatRoom(String roomId) {
        chatRooms.remove(roomId);
    }

    // 채팅방에 있는 사용자 목록 조회
    public Set<String> getUsersInRoom(String roomId) {
        return chatRooms.get(roomId);
    }

//    public List<BoardMemberDTO> searchChatMembers(String category, String search) {
//        try {
//            return chatMapper.searchChatMembers(category, search);
//        } catch (Exception e) {
//            throw new RuntimeException("서비스 - 채팅상대를 찾다가 에러뜸", e);
//        }
//    }
    
    public List<BoardMemberDTO> searchChatMembers(String category, String search) {
        return msgMapper.searchChatMembers(category, search);
    }
	
    
    //아래론 쪽지 관련
    
    
    // 간결하게
//    public void sendMessage(ChatMsgDTO dto) {
//    	msgMapper.insertMsg(dto);
//    	msgMapper.insertRMsg(dto);
//    }
    
//    	복잔한 데이터 처리에 용이하지만 무거운 버전 
    public void sendMessage(ChatMsgDTO msgDTO) {
        logger.debug("전송할 메시지 정보: " + msgDTO);

        msgDTO.setMsgSendTime(LocalDateTime.now());
        msgDTO.setMsgCheck('N'); // 'N'으로 설정 (char 타입 유지)
        msgMapper.insertMsg(msgDTO);

        // 마지막으로 삽입된 ID를 가져옵니다.
        String msgNo = msgDTO.getMsgNo();
        logger.debug("삽입된 메시지 ID: " + msgNo);

        // RMsgDTO 세부 사항 채우기
        msgDTO.setMsgNo(msgNo);
        msgDTO.setMsgReceiveTime(LocalDateTime.now());
        msgDTO.setMsgCheck('N'); // 'N'으로 설정 (char 타입 유지)

        msgMapper.insertRMsg(msgDTO);
    }



    public List<ChatMsgDTO> getReceivedMsg(String id) {
        return msgMapper.selectRMsgsByReciver(id);
    }

    public List<ChatMsgDTO> getSentMsg(String id) {
        return msgMapper.selectMsgsBySender(id);
    }
    
    public ChatMsgDTO getMsgById(String msgNo) {  // 특정 메시지를 ID로 조회하는 메서드
        return msgMapper.selectMsgById(msgNo);
    }
    
    
}