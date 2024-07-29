package com.gae.dto;

import org.apache.ibatis.type.Alias;
import java.util.Date;

@Alias("show")
public class ShowDTO {
    private int showNo;
    private String mId;
    private String showTitle;
    private String showContent;
    private String pNick;
    private Date showDate;
    private int showCount;

    public ShowDTO() { }

    public ShowDTO(int showNo, String mId, String showTitle, String showContent, String pNick, Date showDate, int showCount) {
        super();
        this.showNo = showNo;
        this.mId = mId;
        this.showTitle = showTitle;
        this.showContent = showContent;
        this.pNick = pNick;
        this.showDate = showDate;
        this.showCount = showCount;
    }

    // Getters and Setters

    public int getShowNo() {
        return showNo;
    }

    public void setShowNo(int showNo) {
        this.showNo = showNo;
    }

    public String getMId() {
        return mId;
    }

    public void setMId(String mId) {
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

    public String getPNick() {
        return pNick;
    }

    public void setPNick(String pNick) {
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
}
