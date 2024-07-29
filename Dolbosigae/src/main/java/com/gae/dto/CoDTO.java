
package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("co")
public class CoDTO {
	private int coId;
	private String coDistinction;
	private String coName;
	private String coHour;
	private String coTel;
	private String coAddress;
	private String coSatHour;
	private String coSunHour;
	private String coDay;
	private String coImg;
	private String coImgNo;
	private String coImgPath;
	
	public CoDTO() {	}

	public CoDTO(int coId, String coDistinction, String coName, String coHour, String coTel, String coAddress,
			String coSatHour, String coSunHour, String coDay, String coImg, String coImgNo, String coImgPath) {
		super();
		this.coId = coId;
		this.coDistinction = coDistinction;
		this.coName = coName;
		this.coHour = coHour;
		this.coTel = coTel;
		this.coAddress = coAddress;
		this.coSatHour = coSatHour;
		this.coSunHour = coSunHour;
		this.coDay = coDay;
		this.coImg = coImg;
		this.coImgNo = coImgNo;
		this.coImgPath = coImgPath;
	}

	
	/**
	 * @return the coId
	 */
	public int getCoId() {
		return coId;
	}

	/**
	 * @param coId the coId to set
	 */
	public void setCoId(int coId) {
		this.coId = coId;
	}

	/**
	 * @return the coDistinction
	 */
	public String getCoDistinction() {
		return coDistinction;
	}

	/**
	 * @param coDistinction the coDistinction to set
	 */
	public void setCoDistinction(String coDistinction) {
		this.coDistinction = coDistinction;
	}

	/**
	 * @return the coName
	 */
	public String getCoName() {
		return coName;
	}

	/**
	 * @param coName the coName to set
	 */
	public void setCoName(String coName) {
		this.coName = coName;
	}

	/**
	 * @return the coHour
	 */
	public String getCoHour() {
		return coHour;
	}

	/**
	 * @param coHour the coHour to set
	 */
	public void setCoHour(String coHour) {
		this.coHour = coHour;
	}

	/**
	 * @return the coTel
	 */
	public String getCoTel() {
		return coTel;
	}

	/**
	 * @param coTel the coTel to set
	 */
	public void setCoTel(String coTel) {
		this.coTel = coTel;
	}

	/**
	 * @return the coAddress
	 */
	public String getCoAddress() {
		return coAddress;
	}

	/**
	 * @param coAddress the coAddress to set
	 */
	public void setCoAddress(String coAddress) {
		this.coAddress = coAddress;
	}

	/**
	 * @return the coSatHour
	 */
	public String getCoSatHour() {
		return coSatHour;
	}

	/**
	 * @param coSatHour the coSatHour to set
	 */
	public void setCoSatHour(String coSatHour) {
		this.coSatHour = coSatHour;
	}

	/**
	 * @return the coSunHour
	 */
	public String getCoSunHour() {
		return coSunHour;
	}

	/**
	 * @param coSunHour the coSunHour to set
	 */
	public void setCoSunHour(String coSunHour) {
		this.coSunHour = coSunHour;
	}

	/**
	 * @return the coDay
	 */
	public String getCoDay() {
		return coDay;
	}

	/**
	 * @param coDay the coDay to set
	 */
	public void setCoDay(String coDay) {
		this.coDay = coDay;
	}

	/**
	 * @return the coImg
	 */
	public String getCoImg() {
		return coImg;
	}

	/**
	 * @param coImg the coImg to set
	 */
	public void setCoImg(String coImg) {
		this.coImg = coImg;
	}

	/**
	 * @return the coImgNo
	 */
	public String getCoImgNo() {
		return coImgNo;
	}

	/**
	 * @param coImgNo the coImgNo to set
	 */
	public void setCoImgNo(String coImgNo) {
		this.coImgNo = coImgNo;
	}

	/**
	 * @return the coImgPath
	 */
	public String getCoImgPath() {
		return coImgPath;
	}

	/**
	 * @param coImgPath the coImgPath to set
	 */
	public void setCoImgPath(String coImgPath) {
		this.coImgPath = coImgPath;
	}

	@Override
	public String toString() {
		return "CoDTO [coId=" + coId + ", coDistinction=" + coDistinction + ", coName=" + coName + ", coHour=" + coHour
				+ ", coTel=" + coTel + ", coAddress=" + coAddress + ", coSatHour=" + coSatHour + ", coSunHour="
				+ coSunHour + ", coDay=" + coDay + ", coImg=" + coImg + ", coImgNo=" + coImgNo + ", coImgPath="
				+ coImgPath + "]";
	}

	


}
