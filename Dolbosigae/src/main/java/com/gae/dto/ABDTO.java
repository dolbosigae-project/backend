package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("AB")
public class ABDTO {
	private String ABId;
	private String SHId;
	private String ABDate;
	private String ABLocation;
	private String ABStatus;
	private String ABBreed;
	private String ABGender;
	private String ABAge;
	private String ABWeight;
	private String ABColor;
	private String ABCharacter;
	private String ABImg;
	private String ABregion;
	private String ABLati;
	private String ABLong;
	
	private String SHRegion;
	private String SHAddress;
	private String SHName;
	private String SHTel;
	private String SHHour;
	private double SHLatitude;
	private double SHLongitude;
	
	public String getSHRegion() {
		return SHRegion;
	}
	public void setSHRegion(String sHRegion) {
		SHRegion = sHRegion;
	}
	public String getSHAddress() {
		return SHAddress;
	}
	public void setSHAddress(String sHAddress) {
		SHAddress = sHAddress;
	}
	public String getSHName() {
		return SHName;
	}
	public void setSHName(String sHName) {
		SHName = sHName;
	}
	public String getSHTel() {
		return SHTel;
	}
	public void setSHTel(String sHTel) {
		SHTel = sHTel;
	}
	public String getSHHour() {
		return SHHour;
	}
	public void setSHHour(String sHHour) {
		SHHour = sHHour;
	}
	public double getSHLatitude() {
		return SHLatitude;
	}
	public void setSHLatitude(double sHLatitude) {
		SHLatitude = sHLatitude;
	}
	public double getSHLongitude() {
		return SHLongitude;
	}
	public void setSHLongitude(double sHLongitude) {
		SHLongitude = sHLongitude;
	}
	public String getABId() {
		return ABId;
	}
	public void setABId(String aBId) {
		ABId = aBId;
	}
	public String getSHId() {
		return SHId;
	}
	public void setSHId(String sHId) {
		SHId = sHId;
	}
	public String getABDate() {
		return ABDate;
	}
	public void setABDate(String aBDate) {
		ABDate = aBDate;
	}
	public String getABLocation() {
		return ABLocation;
	}
	public void setABLocation(String aBLocation) {
		ABLocation = aBLocation;
	}
	public String getABStatus() {
		return ABStatus;
	}
	public void setABStatus(String aBStatus) {
		ABStatus = aBStatus;
	}
	public String getABBreed() {
		return ABBreed;
	}
	public void setABBreed(String aBBreed) {
		ABBreed = aBBreed;
	}
	public String getABGender() {
		return ABGender;
	}
	public void setABGender(String aBGender) {
		ABGender = aBGender;
	}
	public String getABAge() {
		return ABAge;
	}
	public void setABAge(String aBAge) {
		ABAge = aBAge;
	}
	public String getABWeight() {
		return ABWeight;
	}
	public void setABWeight(String aBWeight) {
		ABWeight = aBWeight;
	}
	public String getABColor() {
		return ABColor;
	}
	public void setABColor(String aBColor) {
		ABColor = aBColor;
	}
	public String getABCharacter() {
		return ABCharacter;
	}
	public void setABCharacter(String aBCharacter) {
		ABCharacter = aBCharacter;
	}
	public String getABImg() {
		return ABImg;
	}
	public void setABImg(String aBImg) {
		ABImg = aBImg;
	}
	public String getABregion() {
		return ABregion;
	}
	public void setABregion(String aBregion) {
		ABregion = aBregion;
	}
	public String getABLati() {
		return ABLati;
	}
	public void setABLati(String aBLati) {
		ABLati = aBLati;
	}
	public String getABLong() {
		return ABLong;
	}
	public void setABLong(String aBLong) {
		ABLong = aBLong;
	}
	
	@Override
	public String toString() {
		return "ABDTO [ABId=" + ABId + ", SHId=" + SHId + ", ABDate=" + ABDate + ", ABLocation=" + ABLocation
				+ ", ABStatus=" + ABStatus + ", ABBreed=" + ABBreed + ", ABGender=" + ABGender + ", ABAge=" + ABAge
				+ ", ABWeight=" + ABWeight + ", ABColor=" + ABColor + ", ABCharacter=" + ABCharacter + ", ABImg="
				+ ABImg + ", ABregion=" + ABregion + ", ABLati=" + ABLati + ", ABLong=" + ABLong + ", SHRegion="
				+ SHRegion + ", SHAddress=" + SHAddress + ", SHName=" + SHName + ", SHTel=" + SHTel + ", SHHour="
				+ SHHour + ", SHLatitude=" + SHLatitude + ", SHLongitude=" + SHLongitude + "]";
	}
	
}
