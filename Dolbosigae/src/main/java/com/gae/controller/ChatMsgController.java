package com.gae.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.BoardMemberDTO;
import com.gae.dto.ChatMsgDTO;
import com.gae.service.ChatMsgService;

@RestController
public class ChatMsgController {

    private static final Logger logger = LoggerFactory.getLogger(ChatMsgController.class);

    private final SimpMessageSendingOperations messagingTemplate;
    
    @Autowired
    private ChatMsgService chatMsgService;

    public ChatMsgController(SimpMessageSendingOperations messagingTemplate, ChatMsgService chatMsgService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMsgService = chatMsgService;
    }

    @MessageMapping("/chat.createRoom/{userA}/{userB}")
    public void createRoom(@Payload ChatMsgDTO chatMsg, @DestinationVariable String userA, @DestinationVariable String userB, SimpMessageHeaderAccessor headerAccessor) {
        String roomId = generateRoomId(userA, userB);
        if (chatMsgService.createRoom(roomId, userA, userB)) {
            logger.info("방이 생성되었습니다: " + roomId);
            headerAccessor.getSessionAttributes().put("username", userA);
            chatMsg.setContent("채팅방이 생성되었습니다. 입장 요청을 보냈습니다.");
            messagingTemplate.convertAndSendToUser(userA, "/queue/messages", chatMsg);
            logger.info("초대 메시지가 {}에게 전송되었습니다.", userA);

            ChatMsgDTO inviteMessage = new ChatMsgDTO();
            inviteMessage.setSender(userA);
            inviteMessage.setContent(userA + "님이 채팅방에 초대했습니다.");
            messagingTemplate.convertAndSendToUser(userB, "/queue/messages", inviteMessage);
            logger.info("초대 메시지가 {}에게 전송되었습니다.", userB);
        } else {
            logger.warn("방 생성에 실패했습니다: " + roomId);
            chatMsg.setContent("채팅방 생성에 실패했습니다.");
            messagingTemplate.convertAndSendToUser(userA, "/queue/messages", chatMsg);
        }
    }

    @MessageMapping("/chat.acceptInvite/{roomId}/{userB}")
    public void acceptInvite(@Payload ChatMsgDTO chatMsg, @DestinationVariable String roomId, @DestinationVariable String userB, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", userB);
        chatMsg.setContent(userB + "님이 초대를 수락했습니다.");
        messagingTemplate.convertAndSend("/topic/" + roomId, chatMsg);
        logger.info("{}님이 초대를 수락했습니다. 방 ID: {}", userB, roomId);

        ChatMsgDTO joinMessage = new ChatMsgDTO();
        joinMessage.setSender(userB);
        joinMessage.setContent(userB + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/topic/" + roomId, joinMessage);
    }

    @MessageMapping("/chat.rejectInvite/{roomId}/{userB}")
    public void rejectInvite(@Payload ChatMsgDTO chatMsg, @DestinationVariable String roomId, @DestinationVariable String userB) {
    	chatMsgService.removeUserFromRoom(roomId, userB);
    	chatMsg.setContent(userB + "님이 초대를 거절했습니다. 채팅방이 삭제되었습니다.");
        messagingTemplate.convertAndSendToUser(chatMsg.getSender(), "/queue/messages", chatMsg);
        logger.info("{}님이 초대를 거절했습니다. 방 ID: {}", userB, roomId);

        chatMsgService.removeChatRoom(roomId);
    }

    @MessageMapping("/chat/{roomId}")
    public void sendMessage(@Payload ChatMsgDTO chatMsg, @DestinationVariable String roomId) {
        logger.info("메시지를 수신했습니다. 방 ID: {}, 내용: {}", roomId, chatMsg.getContent());
        messagingTemplate.convertAndSend("/topic/" + roomId, chatMsg);
        logger.info("메시지가 방 {}에 전송되었습니다. 내용: {}", roomId, chatMsg.getContent());
    }

    private String generateRoomId(String userA, String userB) {
        return (userA.compareTo(userB) < 0) ? userA + "-" + userB : userB + "-" + userA;
    }

    @GetMapping("/chat/IdNick/search")
    public ResponseEntity<?> searchChatMembers(@RequestParam String category, @RequestParam String search) {
        try {
        	List<BoardMemberDTO> searchat = chatMsgService.searchChatMembers(category, search);
            return ResponseEntity.ok(searchat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( "Internal Server에서 에러난거임");
        }
    }
    
    
    
    //아래론 쪽지 관련
    @GetMapping("/msg/R_Msg")
    public ResponseEntity<List<ChatMsgDTO>> getReceivedMsg(@RequestParam String id) {
        List<ChatMsgDTO> msgs = chatMsgService.getReceivedMsg(id);
        return ResponseEntity.ok(msgs);
    }

    @GetMapping("/msg/S_Msg")
    public ResponseEntity<List<ChatMsgDTO>> getSentMsg(@RequestParam String id) {
        List<ChatMsgDTO> msgs = chatMsgService.getSentMsg(id);
        return ResponseEntity.ok(msgs);
    }
  
    @GetMapping("/msg/message/{msgNo}")
    public ResponseEntity<ChatMsgDTO> getMsgById(@PathVariable String msgNo) {
    	ChatMsgDTO msg = chatMsgService.getMsgById(msgNo);
        return ResponseEntity.ok(msg);
    }
    
    @PostMapping("/msg/sendMsg")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMsgDTO msgDTO) {
        try {
            logger.debug("받은 메시지 정보: " + msgDTO);
            chatMsgService.sendMessage(msgDTO);
            return ResponseEntity.ok("메시지가 성공적으로 전송되었습니다.");
        } catch (Exception e) {
            logger.error("메시지 전송 중 오류 발생: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("메시지 전송 중 오류가 발생했습니다.");
        }
    }
    
    
    
}
