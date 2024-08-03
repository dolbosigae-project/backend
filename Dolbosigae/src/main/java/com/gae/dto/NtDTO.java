package com.gae.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class NtDTO {
    private int notifId;
    private int msgId;
    private String rId;
    private Timestamp notifTime;
    private String isSeen;

    public int getNotifId() {
        return notifId;
    }

    public void setNotifId(int notifId) {
        this.notifId = notifId;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public Timestamp getNotifTime() {
        return notifTime;
    }

    public void setNotifTime(Timestamp notifTime) {
        this.notifTime = notifTime;
    }

    public void setNotifTime(LocalDateTime notifTime) {
        this.notifTime = Timestamp.valueOf(notifTime);
    }

    public String getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(String isSeen) {
        this.isSeen = isSeen;
    }
}