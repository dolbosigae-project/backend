package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("shelter")
public class ShelterDTO {
	private String shID;
	private String shRegion;
	private String shDis;
	private String shAddress;
	private String shName;
	private String shTel;
	private String shAnimalType;
	private String shHour;
	
	public ShelterDTO() {

	}

	public ShelterDTO(String shID, String shRegion, String shDis, String shAddress, String shName, String shTel,
			String shAnimalType, String shHour) {
		super();
		this.shID = shID;
		this.shRegion = shRegion;
		this.shDis = shDis;
		this.shAddress = shAddress;
		this.shName = shName;
		this.shTel = shTel;
		this.shAnimalType = shAnimalType;
		this.shHour = shHour;
	}

	public String getShID() {
		return shID;
	}

	public void setShID(String shID) {
		this.shID = shID;
	}

	public String getShRegion() {
		return shRegion;
	}

	public void setShRegion(String shRegion) {
		this.shRegion = shRegion;
	}

	public String getShDis() {
		return shDis;
	}

	public void setShDis(String shDis) {
		this.shDis = shDis;
	}

	public String getShAddress() {
		return shAddress;
	}

	public void setShAddress(String shAddress) {
		this.shAddress = shAddress;
	}

	public String getShName() {
		return shName;
	}

	public void setShName(String shName) {
		this.shName = shName;
	}

	public String getShTel() {
		return shTel;
	}

	public void setShTel(String shTel) {
		this.shTel = shTel;
	}

	public String getShAnimalType() {
		return shAnimalType;
	}

	public void setShAnimalType(String shAnimalType) {
		this.shAnimalType = shAnimalType;
	}

	public String getShHour() {
		return shHour;
	}

	public void setShHour(String shHour) {
		this.shHour = shHour;
	}

	@Override
	public String toString() {
		return "ShelterDTO [shID=" + shID + ", shRegion=" + shRegion + ", shDis=" + shDis + ", shAddress=" + shAddress
				+ ", shName=" + shName + ", shTel=" + shTel + ", shAnimalType=" + shAnimalType + ", shHour=" + shHour
				+ "]";
	}
	
}
