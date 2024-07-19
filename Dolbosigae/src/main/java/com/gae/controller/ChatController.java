package com.gae.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.ChatMessage;
import com.gae.service.ChatRoomService;

@RestController
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomService chatRoomService;

    public ChatController(SimpMessageSendingOperations messagingTemplate, ChatRoomService chatRoomService) {
        this.messagingTemplate = messagingTemplate;
        this.chatRoomService = chatRoomService;
    }

    @MessageMapping("/chat.createRoom/{userA}/{userB}")
    public void createRoom(@Payload ChatMessage chatMessage, @DestinationVariable String userA, @DestinationVariable String userB, SimpMessageHeaderAccessor headerAccessor) {
        String roomId = generateRoomId(userA, userB);
        if (chatRoomService.createRoom(roomId, userA, userB)) {
            logger.info("방이 생성되었습니다: " + roomId);
            headerAccessor.getSessionAttributes().put("username", userA);
            chatMessage.setContent("채팅방이 생성되었습니다. 입장 요청을 보냈습니다.");
            messagingTemplate.convertAndSendToUser(userA, "/queue/messages", chatMessage);
            logger.info("초대 메시지가 {}에게 전송되었습니다.", userA);

            ChatMessage inviteMessage = new ChatMessage();
            inviteMessage.setSender(userA);
            inviteMessage.setContent(userA + "님이 채팅방에 초대했습니다.");
            messagingTemplate.convertAndSendToUser(userB, "/queue/messages", inviteMessage);
            logger.info("초대 메시지가 {}에게 전송되었습니다.", userB);
        } else {
            logger.warn("방 생성에 실패했습니다: " + roomId);
            chatMessage.setContent("채팅방 생성에 실패했습니다.");
            messagingTemplate.convertAndSendToUser(userA, "/queue/messages", chatMessage);
        }
    }

    @MessageMapping("/chat.acceptInvite/{roomId}/{userB}")
    public void acceptInvite(@Payload ChatMessage chatMessage, @DestinationVariable String roomId, @DestinationVariable String userB, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", userB);
        chatMessage.setContent(userB + "님이 초대를 수락했습니다.");
        messagingTemplate.convertAndSend("/topic/" + roomId, chatMessage);
        logger.info("{}님이 초대를 수락했습니다. 방 ID: {}", userB, roomId);

        ChatMessage joinMessage = new ChatMessage();
        joinMessage.setSender(userB);
        joinMessage.setContent(userB + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/topic/" + roomId, joinMessage);
    }

    @MessageMapping("/chat.rejectInvite/{roomId}/{userB}")
    public void rejectInvite(@Payload ChatMessage chatMessage, @DestinationVariable String roomId, @DestinationVariable String userB) {
        chatRoomService.removeUserFromRoom(roomId, userB);
        chatMessage.setContent(userB + "님이 초대를 거절했습니다. 채팅방이 삭제되었습니다.");
        messagingTemplate.convertAndSendToUser(chatMessage.getSender(), "/queue/messages", chatMessage);
        logger.info("{}님이 초대를 거절했습니다. 방 ID: {}", userB, roomId);

        chatRoomService.removeChatRoom(roomId);
    }

    @MessageMapping("/chat/{roomId}")
    public void sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable String roomId) {
        messagingTemplate.convertAndSend("/topic/" + roomId, chatMessage);
        logger.info("메시지가 방 {}에 전송되었습니다. 내용: {}", roomId, chatMessage.getContent());
    }

    private String generateRoomId(String userA, String userB) {
        return (userA.compareTo(userB) < 0) ? userA + "-" + userB : userB + "-" + userA;
    }

  
    
    
}
