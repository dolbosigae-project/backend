package com.gae.dto;

import java.time.LocalDateTime;


public class ChatMsgDTO {
    private String sender;
    private String reciver;
	private String content;
	
	private String msgNo;
    private String mIdFrom;
    private String mIdTo;
    private String msgTitle; // 메시지 제목
    private String msgContent;
    private LocalDateTime msgSendTime;
    private char msgCheck;
    private LocalDateTime msgReceiveTime;
//    private MessageType type;
    

//    public enum MessageType {
//        CHAT,
//        JOIN,
//        LEAVE
//    }

    
    
    public String getMsgNo() {
		return msgNo;
	}

    public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}

	public String getMsgTitle() {
		return msgTitle;
	}


	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}


	public String getmIdFrom() {
		return mIdFrom;
	}


	public void setmIdFrom(String mIdFrom) {
		this.mIdFrom = mIdFrom;
	}


	public String getmIdTo() {
		return mIdTo;
	}


	public void setmIdTo(String mIdTo) {
		this.mIdTo = mIdTo;
	}


	public String getMsgContent() {
		return msgContent;
	}


	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}


	public LocalDateTime getMsgSendTime() {
		return msgSendTime;
	}


	public void setMsgSendTime(LocalDateTime msgSendTime) {
		this.msgSendTime = msgSendTime;
	}


	public char getMsgCheck() {
		return msgCheck;
	}


	public void setMsgCheck(char msgCheck) {
		this.msgCheck = msgCheck;
	}


	public LocalDateTime getMsgReceiveTime() {
		return msgReceiveTime;
	}


	public void setMsgReceiveTime(LocalDateTime msgReceiveTime) {
		this.msgReceiveTime = msgReceiveTime;
	}


	public ChatMsgDTO() {
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

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	@Override
	public String toString() {
		return "ChatMsgDTO [sender=" + sender + ", reciver=" + reciver + ", content=" + content + ", msgNo=" + msgNo
				+ ", mIdFrom=" + mIdFrom + ", mIdTo=" + mIdTo + ", msgTitle=" + msgTitle + ", msgContent=" + msgContent
				+ ", msgSendTime=" + msgSendTime + ", msgCheck=" + msgCheck + ", msgReceiveTime=" + msgReceiveTime
				+ "]";
	}
    
    
    

}