package com.gae.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShowDTO {
    @JsonProperty("showNo")
    private int showNo;
    
    @JsonProperty("mId")
    private String mId;
    
    @JsonProperty("showTitle")
    private String showTitle;
    
    @JsonProperty("showContent")
    private String showContent;
    
    @JsonProperty("pNick")
    private String pNick;
    
    @JsonProperty("showDate")
    private Date showDate;
    
    @JsonProperty("showCount")
    private int showCount;

    @JsonProperty("showImagePath") // 이미지 URL을 저장할 필드
    private String showImagePath;
    
    private String showImage; // Base64로 인코딩된 이미지 데이터

    // Getters and Setters
    public int getShowNo() {
        return showNo;
    }

    public void setShowNo(int showNo) {
        this.showNo = showNo;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getShowContent() {
        return showContent;
    }

    public void setShowContent(String showContent) {
        this.showContent = showContent;
    }

    public String getpNick() {
        return pNick;
    }

    public void setpNick(String pNick) {
        this.pNick = pNick;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public int getShowCount() {
        return showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public String getShowImagePath() {
        return showImagePath;
    }

    public void setShowImagePath(String showImagePath) {
        this.showImagePath = showImagePath;
    }

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }
}
