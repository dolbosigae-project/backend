package com.gae.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.BoardMemberDTO;
import com.gae.dto.ChatMsgDTO;
import com.gae.dto.NtDTO;
import com.gae.service.ChatMsgService;

@RestController
public class ChatMsgController {

    private static final Logger logger = LoggerFactory.getLogger(ChatMsgController.class);

//    private final SimpMessageSendingOperations messagingTemplate;
    
    @Autowired
    private ChatMsgService chatMsgService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ChatMsgController(SimpMessageSendingOperations messagingTemplate, ChatMsgService chatMsgService) {
//        this.messagingTemplate = messagingTemplate;
        this.chatMsgService = chatMsgService;
    }

 // 쪽지 관련 엔드포인트
    //알림은 websocket으로 
    @PostMapping("/msg/send")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatMsgDTO message, @RequestParam boolean isAdmin) {
        chatMsgService.sendMessage(message, isAdmin);
        messagingTemplate.convertAndSendToUser(message.getrId(), "/queue/notifications", "새 쪽지가 도착했습니다.");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/msg/received/{rId}")
    public ResponseEntity<List<ChatMsgDTO>> getReceivedMessages(@PathVariable String rId) {
        List<ChatMsgDTO> messages = chatMsgService.getReceivedMsg(rId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/msg/sent/{sId}")
    public ResponseEntity<List<ChatMsgDTO>> getSentMessages(@PathVariable String sId) {
        List<ChatMsgDTO> messages = chatMsgService.getSentMsg(sId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/msg/message/{msgId}")
    public ResponseEntity<ChatMsgDTO> getMessageById(@PathVariable int msgId) {
        ChatMsgDTO message = chatMsgService.getMsgById(msgId);
//        System.out.println("메시지 아이디로 조회하는 컨트롤러 통과");
        return ResponseEntity.ok(message);
    }

    @GetMapping("/msg/notifications/{rId}")
    public ResponseEntity<List<NtDTO>> getNotifications(@PathVariable String rId) {
        List<NtDTO> notifications = chatMsgService.getNotifications(rId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/msg/notifications/seen/{notifId}")
    public ResponseEntity<Void> markNotificationAsSeen(@PathVariable int notifId) {
        chatMsgService.markNotificationAsSeen(notifId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/msg/delete/{msgId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int msgId) {
        chatMsgService.deleteMsg(msgId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping(value = "/msg/send", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatMsgDTO chatMsgDTO) {
        chatMsgService.sendMessage(chatMsgDTO, false);
        return ResponseEntity.ok().build();
    }
    
    

    @GetMapping("/chat/usersInRoom/{roomId}")
    public ResponseEntity<Set<String>> getUsersInRoom(@PathVariable String roomId) {
        Set<String> users = chatMsgService.getUsersInRoom(roomId);
        return ResponseEntity.ok(users);
    }

    // WebSocket 메시지 매핑
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMsgDTO broadcastMessage(ChatMsgDTO message) {
        chatMsgService.sendMessage(message, false);
        return message;
    }

    @MessageMapping("/msg.sendMessage")
    public void sendPrivateMessage(ChatMsgDTO message) {
        chatMsgService.sendMessage(message, false);
        messagingTemplate.convertAndSendToUser(message.getrId(), "/queue/notifications", "새 쪽지가 도착했습니다.");
    }
    
    @MessageMapping("/chat.createRoom/{userA}/{userB}")
    public void createRoom(@Payload ChatMsgDTO dto, @DestinationVariable String userA, @DestinationVariable String userB, SimpMessageHeaderAccessor headerAccessor) {
        String roomId = generateRoomId(userA, userB);
        logger.info("createRoom 호출됨 - userA: {}, userB: {}", userA, userB); // 디버깅 로그 추가
        if (chatMsgService.createRoom(roomId, userA, userB)) {
            logger.info("방이 생성되었습니다: " + roomId);
            headerAccessor.getSessionAttributes().put("username", userA);
            dto.setContent("채팅방이 생성되었습니다. 입장 요청을 보냈습니다.");
            messagingTemplate.convertAndSendToUser(userA, "/queue/messages", dto);
            logger.info("초대 메시지가 {}에게 전송되었습니다.", userA);

            ChatMsgDTO inviteMessage = new ChatMsgDTO();
            inviteMessage.setSender(userA);
            inviteMessage.setContent(userA + "님이 채팅방에 초대했습니다.");
            messagingTemplate.convertAndSendToUser(userB, "/queue/messages", inviteMessage);
            logger.info("초대 메시지가 {}에게 전송되었습니다.", userB);
        } else {
            logger.warn("방 생성에 실패했습니다: " + roomId);
            dto.setContent("채팅방 생성에 실패했습니다.");
            messagingTemplate.convertAndSendToUser(userA, "/queue/messages", dto);
        }
    }
    
    @MessageMapping("/chat.request/{receiverId}")
    public void handleChatRequest(@DestinationVariable String receiverId, ChatMsgDTO chatMessage) {
        // receiverId에 해당하는 사용자가 연결된 세션을 찾아 메시지 전송
        messagingTemplate.convertAndSendToUser(receiverId, "/queue/chat", chatMessage);
    }

    @MessageMapping("/chat.acceptInvite/{roomId}/{userB}")
    public void acceptInvite(@Payload ChatMsgDTO dto, @DestinationVariable String roomId, @DestinationVariable String userB, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", userB);
        dto.setContent(userB + "님이 초대를 수락했습니다.");
        messagingTemplate.convertAndSend("/topic/" + roomId, dto);
        logger.info("{}님이 초대를 수락했습니다. 방 ID: {}", userB, roomId);

        ChatMsgDTO joinMessage = new ChatMsgDTO();
        joinMessage.setSender(userB);
        joinMessage.setContent(userB + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/topic/" + roomId, joinMessage);
    }

    @MessageMapping("/chat.rejectInvite/{roomId}/{userB}")
    public void rejectInvite(@Payload ChatMsgDTO dto, @DestinationVariable String roomId, @DestinationVariable String userB) {
        chatMsgService.removeUserFromRoom(roomId, userB);
        dto.setContent(userB + "님이 초대를 거절했습니다. 채팅방이 삭제되었습니다.");
        messagingTemplate.convertAndSendToUser(dto.getSender(), "/queue/messages", dto);
        logger.info("{}님이 초대를 거절했습니다. 방 ID: {}", userB, roomId);

        chatMsgService.removeChatRoom(roomId);
    }

    @MessageMapping("/chat/{roomId}")
    public void sendMessage(@Payload ChatMsgDTO dto, @DestinationVariable String roomId) {
        logger.info("메시지를 수신했습니다. 방 ID: {}, 내용: {}", roomId, dto.getContent());
        messagingTemplate.convertAndSend("/topic/" + roomId, dto);
        logger.info("메시지가 방 {}에 전송되었습니다. 내용: {}", roomId, dto.getContent());
    }

    private String generateRoomId(String userA, String userB) {
        return (userA.compareTo(userB) < 0) ? userA + "-" + userB : userB + "-" + userA;
    }

    @GetMapping("/chat/IdNick/search")
    public ResponseEntity<?> searchChatMembers(@RequestParam String category, @RequestParam String search) {
        try {
            logger.info("회원 검색 요청 - 카테고리: {}, 검색어: {}", category, search); // 디버깅 로그 추가
            List<BoardMemberDTO> searchat = chatMsgService.searchChatMembers(category, search);
            return ResponseEntity.ok(searchat);
        } catch (Exception e) {
            logger.error("회원 검색 중 오류 발생: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server에서 에러난거임");
        }
    }
    
    
}
