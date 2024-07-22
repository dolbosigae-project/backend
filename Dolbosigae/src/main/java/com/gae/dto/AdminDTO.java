package com.gae.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

@Alias("Admin")
public class AdminDTO {
	private int adminNo;
	private String adminMemberId;
	private String adminTitle;
	private String adminContent;
	private Date adminDate;
	private int adminCommentCount;
	private String adminNick;
	private int adminCommentNo;
	private String adminCommentContent;
	private Date adminCommentDate;
	private int FAQId;
	private String FAQTitle;
	private String FAQContent;
	private Date FAQDate;
	public int getAdminNo() {
		return adminNo;
	}
	public void setAdminNo(int adminNo) {
		this.adminNo = adminNo;
	}
	public String getAdminMemberId() {
		return adminMemberId;
	}
	public void setAdminMemberId(String adminMemberId) {
		this.adminMemberId = adminMemberId;
	}
	public String getAdminTitle() {
		return adminTitle;
	}
	public void setAdminTitle(String adminTitle) {
		this.adminTitle = adminTitle;
	}
	public String getAdminContent() {
		return adminContent;
	}
	public void setAdminContent(String adminContent) {
		this.adminContent = adminContent;
	}
	public Date getAdminDate() {
		return adminDate;
	}
	public void setAdminDate(Date adminDate) {
		this.adminDate = adminDate;
	}
	public int getAdminCommentCount() {
		return adminCommentCount;
	}
	public void setAdminCommentCount(int adminCommentCount) {
		this.adminCommentCount = adminCommentCount;
	}
	public String getAdminNick() {
		return adminNick;
	}
	public void setAdminNick(String adminNick) {
		this.adminNick = adminNick;
	}
	public int getAdminCommentNo() {
		return adminCommentNo;
	}
	public void setAdminCommentNo(int adminCommentNo) {
		this.adminCommentNo = adminCommentNo;
	}
	public String getAdminCommentContent() {
		return adminCommentContent;
	}
	public void setAdminCommentContent(String adminCommentContent) {
		this.adminCommentContent = adminCommentContent;
	}
	public Date getAdminCommentDate() {
		return adminCommentDate;
	}
	public void setAdminCommentDate(Date adminCommentDate) {
		this.adminCommentDate = adminCommentDate;
	}
	public int getFAQId() {
		return FAQId;
	}
	public void setFAQId(int fAQId) {
		FAQId = fAQId;
	}
	public String getFAQTitle() {
		return FAQTitle;
	}
	public void setFAQTitle(String fAQTitle) {
		FAQTitle = fAQTitle;
	}
	public String getFAQContent() {
		return FAQContent;
	}
	public void setFAQContent(String fAQContent) {
		FAQContent = fAQContent;
	}
	public Date getFAQDate() {
		return FAQDate;
	}
	public void setFAQDate(Date fAQDate) {
		FAQDate = fAQDate;
	}
	@Override
	public String toString() {
		return "AdminDTO [adminNo=" + adminNo + ", adminMemberId=" + adminMemberId + ", adminTitle=" + adminTitle
				+ ", adminContent=" + adminContent + ", adminDate=" + adminDate + ", adminCommentCount="
				+ adminCommentCount + ", adminNick=" + adminNick + ", adminCommentNo=" + adminCommentNo
				+ ", adminCommentContent=" + adminCommentContent + ", adminCommentDate=" + adminCommentDate + ", FAQId="
				+ FAQId + ", FAQTitle=" + FAQTitle + ", FAQContent=" + FAQContent + ", FAQDate=" + FAQDate + "]";
	}
}
