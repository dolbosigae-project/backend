package com.gae.dto;

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
	
	public PlDTO() {	}

	public PlDTO(int plId, String plName, String plHour, String plTel, String plAddress, String plInfo,
			String plExpense, String plImg, String plArea, String plDay, String plCity) {
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
	}

	/**
	 * @return the plId
	 */
	public int getPlId() {
		return plId;
	}

	/**
	 * @param plId the plId to set
	 */
	public void setPlId(int plId) {
		this.plId = plId;
	}

	/**
	 * @return the plName
	 */
	public String getPlName() {
		return plName;
	}

	/**
	 * @param plName the plName to set
	 */
	public void setPlName(String plName) {
		this.plName = plName;
	}

	/**
	 * @return the plHour
	 */
	public String getPlHour() {
		return plHour;
	}

	/**
	 * @param plHour the plHour to set
	 */
	public void setPlHour(String plHour) {
		this.plHour = plHour;
	}

	/**
	 * @return the plTel
	 */
	public String getPlTel() {
		return plTel;
	}

	/**
	 * @param plTel the plTel to set
	 */
	public void setPlTel(String plTel) {
		this.plTel = plTel;
	}

	/**
	 * @return the plAddress
	 */
	public String getPlAddress() {
		return plAddress;
	}

	/**
	 * @param plAddress the plAddress to set
	 */
	public void setPlAddress(String plAddress) {
		this.plAddress = plAddress;
	}

	/**
	 * @return the plInfo
	 */
	public String getPlInfo() {
		return plInfo;
	}

	/**
	 * @param plInfo the plInfo to set
	 */
	public void setPlInfo(String plInfo) {
		this.plInfo = plInfo;
	}

	/**
	 * @return the plExpense
	 */
	public String getPlExpense() {
		return plExpense;
	}

	/**
	 * @param plExpense the plExpense to set
	 */
	public void setPlExpense(String plExpense) {
		this.plExpense = plExpense;
	}

	/**
	 * @return the plImg
	 */
	public String getPlImg() {
		return plImg;
	}

	/**
	 * @param plImg the plImg to set
	 */
	public void setPlImg(String plImg) {
		this.plImg = plImg;
	}

	/**
	 * @return the plArea
	 */
	public String getPlArea() {
		return plArea;
	}

	/**
	 * @param plArea the plArea to set
	 */
	public void setPlArea(String plArea) {
		this.plArea = plArea;
	}

	/**
	 * @return the plDay
	 */
	public String getPlDay() {
		return plDay;
	}

	/**
	 * @param plDay the plDay to set
	 */
	public void setPlDay(String plDay) {
		this.plDay = plDay;
	}

	/**
	 * @return the plCity
	 */
	public String getPlCity() {
		return plCity;
	}

	/**
	 * @param plCity the plCity to set
	 */
	public void setPlCity(String plCity) {
		this.plCity = plCity;
	}

	@Override
	public String toString() {
		return "PlDTO [plId=" + plId + ", plName=" + plName + ", plHour=" + plHour + ", plTel=" + plTel + ", plAddress="
				+ plAddress + ", plInfo=" + plInfo + ", plExpense=" + plExpense + ", plImg=" + plImg + ", plArea="
				+ plArea + ", plDay=" + plDay + ", plCity=" + plCity + "]";
	}

	
}