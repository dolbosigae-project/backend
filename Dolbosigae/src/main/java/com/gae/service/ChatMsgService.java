package com.gae.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gae.dto.BoardMemberDTO;
import com.gae.dto.ChatMsgDTO;
import com.gae.dto.NtDTO;
import com.gae.mapper.ChatMsgMapper;

@Service
public class ChatMsgService {
    private final Map<String, Set<String>> chatRooms = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
    
    @Autowired
    private ChatMsgMapper msgMapper;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
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
//    @Transactional
//    public void sendMessage(ChatMsgDTO dto) {
//        logger.debug("전송할 메시지 정보: " + dto);
//
//        if (dto.getMsgSendTime() == null) {
//            dto.setMsgSendTime(LocalDateTime.now());
//        }
//        if (dto.getMsgCheck() == '\u0000') {
//            dto.setMsgCheck('N'); // 'N'으로 설정 (char 타입 유지)
//        }
//
//        // 필수 필드 확인 및 설정
//        if (dto.getmIdFrom() == null || dto.getmIdFrom().isEmpty()) {
//            throw new IllegalArgumentException("Sender (mIdFrom) 가 필요함");
//        }
//        if (dto.getmIdTo() == null || dto.getmIdTo().isEmpty()) {
//            throw new IllegalArgumentException("Receiver (mIdTo) 가 필요함");
//        }
//
//        // MSG_NO를 미리 할당받아 설정
//        int msgNo = msgMapper.selectNextMsgNo();
//        dto.setMsgNo(String.valueOf(msgNo));
//
//        // MSG 테이블에 데이터 삽입
//        msgMapper.insertMsg(dto);
//        logger.debug("MSG 테이블에 데이터 삽입 완료: " + dto);
//
//        // R_MSG 테이블에 데이터 삽입
//        dto.setMsgReceiveTime(LocalDateTime.now());
//        msgMapper.insertRMsg(dto);
//        logger.debug("R_MSG 테이블에 데이터 삽입 완료: " + dto);
//    }

    
    //Sin 쪽지 부분..
 // 쪽지 메시지 관련 메서드들

    @Transactional
    public void sendMessage(ChatMsgDTO message, boolean isAdmin) {
        logger.debug("전송할 메시지 정보: " + message);

        if (message.getSentTime() == null) {
            message.setSentTime(new Timestamp(System.currentTimeMillis()));
        }
        if (message.getIsRead() == null) {
            message.setIsRead("N");
        }

        // 필수 필드 확인 및 설정
        if (message.getsId() == null || message.getsId().isEmpty()) {
            throw new IllegalArgumentException("Sender (sId) 가 필요함");
        }
        if (message.getrId() == null || message.getrId().isEmpty()) {
            throw new IllegalArgumentException("Receiver (rId) 가 필요함");
        }

        // MSG_ID를 미리 할당받아 설정
        int msgId = msgMapper.selectNextMsgId();
        message.setMsgId(msgId);

        // 관리자가 모든 사용자에게 메시지를 보낼 경우
        if (isAdmin && "ALL_USERS".equals(message.getrId())) {
            List<String> allUserIds = msgMapper.getAllUserIds();
            for (String userId : allUserIds) {
                message.setrId(userId);
                msgMapper.insertMsg(message);

//                NtDTO notification = new NtDTO();
//                notification.setMsgId(msgId);
//                notification.setrId(userId);
//                notification.setNotifTime(new Timestamp(System.currentTimeMillis()));
//                msgMapper.insertNotification(notification);
//
//                messagingTemplate.convertAndSendToUser(userId, "/queue/notifications", "새 쪽지가 도착했습니다.");
            }
        } else {
            // 특정 사용자에게 메시지 전송
            msgMapper.insertMsg(message);

//            NtDTO notification = new NtDTO();
//            notification.setMsgId(msgId);
//            notification.setrId(message.getrId());
//            notification.setNotifTime(new Timestamp(System.currentTimeMillis()));
//            msgMapper.insertNotification(notification);
//
//            messagingTemplate.convertAndSendToUser(message.getrId(), "/queue/notifications", "새 쪽지가 도착했습니다.");
        }

        logger.debug("MSG2 테이블에 데이터 삽입 완료: " + message);
    }

    public List<ChatMsgDTO> getReceivedMsg(String rId) {
        return msgMapper.selectMsgsByReceiver(rId);
    }

    public List<ChatMsgDTO> getSentMsg(String sId) {
        return msgMapper.selectMsgsBySender(sId);
    }
    
    public ChatMsgDTO getMsgById(int msgId) {  // 특정 메시지를 ID로 조회하는 메서드
        return msgMapper.selectMsgById(msgId);
    }

    public List<NtDTO> getNotifications(String rId) {
        return msgMapper.getNotificationsByUserId(rId);
    }

    @Transactional
    public void markNotificationAsSeen(int notifId) {
        msgMapper.markNotificationAsSeen(notifId);
    }

	public void deleteMsg(int msgId) {
		msgMapper.deleteMsgById(msgId);
	}
    
    
}