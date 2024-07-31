package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("Shelter")
public class ShelterDTO {
	private String shelterId;
	private String shelterRegion;
	private String shelterAddress;
	private String shelterName;
	private String shelterTel;
	private String shelterHour;
	
	public String getShelterId() {
		return shelterId;
	}
	public void setShelterId(String shelterId) {
		this.shelterId = shelterId;
	}
	public String getShelterRegion() {
		return shelterRegion;
	}
	public void setShelterRegion(String shelterRegion) {
		this.shelterRegion = shelterRegion;
	}
	public String getShelterAddress() {
		return shelterAddress;
	}
	public void setShelterAddress(String shelterAddress) {
		this.shelterAddress = shelterAddress;
	}
	public String getShelterName() {
		return shelterName;
	}
	public void setShelterName(String shelterName) {
		this.shelterName = shelterName;
	}
	public String getShelterTel() {
		return shelterTel;
	}
	public void setShelterTel(String shelterTel) {
		this.shelterTel = shelterTel;
	}
	public String getShelterHour() {
		return shelterHour;
	}
	public void setShelterHour(String shelterHour) {
		this.shelterHour = shelterHour;
	}
	
	@Override
	public String toString() {
		return "ShelterDTO [shelterId=" + shelterId + ", shelterRegion=" + shelterRegion + ", shelterAddress="
				+ shelterAddress + ", shelterName=" + shelterName + ", shelterTel=" + shelterTel + ", shelterHour="
				+ shelterHour + "]";
	}
}
