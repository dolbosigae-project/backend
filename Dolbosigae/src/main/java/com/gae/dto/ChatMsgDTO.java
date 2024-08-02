package com.gae.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ChatMsgDTO {
    private String sender;
    private String receiver;
//    private String content;

	
	  private int msgId;
	  private String sId;
	  private String rId;
	  private String title;
	  private String content;
	  private Timestamp sentTime;
	  private String isRead;
	  private Integer responseTo;
//    private String msgNo;
//    private String mIdFrom;
//    private String mIdTo;
//    private String msgTitle;
//    private String msgContent;
//    private LocalDateTime msgSendTime;
//    private char msgCheck;
//    private LocalDateTime msgReceiveTime;


	    public Integer getResponseTo() {
			return responseTo;
		}

		public void setResponseTo(Integer responseTo) {
			this.responseTo = responseTo;
		}

		 public int getMsgId() {
		        return msgId;
		    }
		
		public void setMsgId(int msgId) {
	        this.msgId = msgId;
	    }

	    public String getsId() {
	        return sId;
	    }

	    public void setsId(String sId) {
	        this.sId = sId;
	    }

	    public String getrId() {
	        return rId;
	    }

	    public void setrId(String rId) {
	        this.rId = rId;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

	    public Timestamp getSentTime() {
	        return sentTime;
	    }

	    public void setSentTime(Timestamp sentTime) {
	        this.sentTime = sentTime;
	    }

	    public String getIsRead() {
	        return isRead;
	    }

	    public void setIsRead(String isRead) {
	        this.isRead = isRead;
	    }
	
	  
	  
	  
	  

//    public String getMsgNo() {
//        return msgNo;
//    }
//
//    public void setMsgNo(String msgNo) {
//        this.msgNo = msgNo;
//    }
//
//    public String getMsgTitle() {
//        return msgTitle;
//    }
//
//    public void setMsgTitle(String msgTitle) {
//        this.msgTitle = msgTitle;
//    }
//
//    public String getmIdFrom() {
//        return mIdFrom;
//    }
//
//    public void setmIdFrom(String mIdFrom) {
//        this.mIdFrom = mIdFrom;
//    }
//
//    public String getmIdTo() {
//        return mIdTo;
//    }
//
//    public void setmIdTo(String mIdTo) {
//        this.mIdTo = mIdTo;
//    }
//
//    public String getMsgContent() {
//        return msgContent;
//    }
//
//    public void setMsgContent(String msgContent) {
//        this.msgContent = msgContent;
//    }
//
//    public LocalDateTime getMsgSendTime() {
//        return msgSendTime;
//    }
//
//    public void setMsgSendTime(LocalDateTime msgSendTime) {
//        this.msgSendTime = msgSendTime;
//    }
//
//    public char getMsgCheck() {
//        return msgCheck;
//    }
//
//    public void setMsgCheck(char msgCheck) {
//        this.msgCheck = msgCheck;
//    }
//
//    public LocalDateTime getMsgReceiveTime() {
//        return msgReceiveTime;
//    }
//
//    public void setMsgReceiveTime(LocalDateTime msgReceiveTime) {
//        this.msgReceiveTime = msgReceiveTime;
//    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

//    @Override
//    public String toString() {
//        return "ChatMsgDTO [sender=" + sender + ", receiver=" + receiver + ", content=" + content + ", msgNo=" + msgNo
//                + ", mIdFrom=" + mIdFrom + ", mIdTo=" + mIdTo + ", msgTitle=" + msgTitle + ", msgContent=" + msgContent
//                + ", msgSendTime=" + msgSendTime + ", msgCheck=" + msgCheck + ", msgReceiveTime=" + msgReceiveTime
//                + "]";
//    }
}