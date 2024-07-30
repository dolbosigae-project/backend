package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("pl")
public class PlDTO {
	private int plId;
	private String plName;
	private String plHour;
	private String plTel;
	private String plAddress;
	private String plInfo;
	private String plExpense;
	private String plImg;
	private String plArea;
	private String plDay;
	private String plCity;
	private String plImgNo;
	private String plImgPath;
	
	public PlDTO() { }

	public PlDTO(int plId, String plName, String plHour, String plTel, String plAddress, String plInfo,
			String plExpense, String plImg, String plArea, String plDay, String plCity, String plImgNo,
			String plImgPath) {  
		super();
		this.plId = plId;
		this.plName = plName;
		this.plHour = plHour;
		this.plTel = plTel;
		this.plAddress = plAddress;
		this.plInfo = plInfo;
		this.plExpense = plExpense;
		this.plImg = plImg;
		this.plArea = plArea;
		this.plDay = plDay;
		this.plCity = plCity;
		this.plImgNo = plImgNo;
		this.plImgPath = plImgPath;  // 필드명 수정
	}

	public int getPlId() {
		return plId;
	}

	public void setPlId(int plId) {
		this.plId = plId;
	}

	public String getPlName() {
		return plName;
	}

	public void setPlName(String plName) {
		this.plName = plName;
	}

	public String getPlHour() {
		return plHour;
	}

	public void setPlHour(String plHour) {
		this.plHour = plHour;
	}

	public String getPlTel() {
		return plTel;
	}

	public void setPlTel(String plTel) {
		this.plTel = plTel;
	}

	public String getPlAddress() {
		return plAddress;
	}

	public void setPlAddress(String plAddress) {
		this.plAddress = plAddress;
	}

	public String getPlInfo() {
		return plInfo;
	}

	public void setPlInfo(String plInfo) {
		this.plInfo = plInfo;
	}

	public String getPlExpense() {
		return plExpense;
	}

	public void setPlExpense(String plExpense) {
		this.plExpense = plExpense;
	}

	public String getPlImg() {
		return plImg;
	}

	public void setPlImg(String plImg) {
		this.plImg = plImg;
	}

	public String getPlArea() {
		return plArea;
	}

	public void setPlArea(String plArea) {
		this.plArea = plArea;
	}

	public String getPlDay() {
		return plDay;
	}

	public void setPlDay(String plDay) {
		this.plDay = plDay;
	}

	public String getPlCity() {
		return plCity;
	}

	public void setPlCity(String plCity) {
		this.plCity = plCity;
	}

	public String getPlImgNo() {
		return plImgNo;
	}

	public void setPlImgNo(String plImgNo) {
		this.plImgNo = plImgNo;
	}

	public String getPlImgPath() { 
		return plImgPath;
	}

	public void setPlImgPath(String plImgPath) { 
		this.plImgPath = plImgPath;
	}

	@Override
	public String toString() {
		return "PlDTO [plId=" + plId + ", plName=" + plName + ", plHour=" + plHour + ", plTel=" + plTel + ", plAddress="
				+ plAddress + ", plInfo=" + plInfo + ", plExpense=" + plExpense + ", plImg=" + plImg + ", plArea="
				+ plArea + ", plDay=" + plDay + ", plCity=" + plCity + ", plImgNo=" + plImgNo + ", plImgPath="
				+ plImgPath + "]";  // 필드명 수정
	}
}