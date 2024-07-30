package com.gae.dto;

import java.time.LocalDateTime;


public class ChatMessage {
    private String sender;
    private String content;
//    private MessageType type;
    

//    public enum MessageType {
//        CHAT,
//        JOIN,
//        LEAVE
//    }

    public ChatMessage() {
    }
    
    
	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

    
    
    

}