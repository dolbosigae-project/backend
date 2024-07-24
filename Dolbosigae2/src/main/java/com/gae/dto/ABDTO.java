package com.gae.dto;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("ab")
public class ABDTO {
	private String abID;
	private String shID;
	private Date abDate;
	private String abLocation;
	private String abStatus;
	private String abBreed;
	private char abGender;
	private String abAge;
	private String abWeight;
	private String abColor;
	private String abRescued;
	private String abCharacter;

	public ABDTO() {
		super();
	}

	public ABDTO(String abID, String shID, Date abDate, String abLocation, String abStatus, String abBreed,
			char abGender, String abAge, String abWeight, String abColor, String abRescued, String abCharacter) {
		super();
		this.abID = abID;
		this.shID = shID;
		this.abDate = abDate;
		this.abLocation = abLocation;
		this.abStatus = abStatus;
		this.abBreed = abBreed;
		this.abGender = abGender;
		this.abAge = abAge;
		this.abWeight = abWeight;
		this.abColor = abColor;
		this.abRescued = abRescued;
		this.abCharacter = abCharacter;
	}

	public String getAbID() {
		return abID;
	}

	public void setAbID(String abID) {
		this.abID = abID;
	}

	public String getShID() {
		return shID;
	}

	public void setShID(String shID) {
		this.shID = shID;
	}

	public Date getAbDate() {
		return abDate;
	}

	public void setAbDate(Date abDate) {
		this.abDate = abDate;
	}

	public String getAbLocation() {
		return abLocation;
	}

	public void setAbLocation(String abLocation) {
		this.abLocation = abLocation;
	}

	public String getAbStatus() {
		return abStatus;
	}

	public void setAbStatus(String abStatus) {
		this.abStatus = abStatus;
	}

	public String getAbBreed() {
		return abBreed;
	}

	public void setAbBreed(String abBreed) {
		this.abBreed = abBreed;
	}

	public char getAbGender() {
		return abGender;
	}

	public void setAbGender(char abGender) {
		this.abGender = abGender;
	}

	public String getAbAge() {
		return abAge;
	}

	public void setAbAge(String abAge) {
		this.abAge = abAge;
	}

	public String getAbWeight() {
		return abWeight;
	}

	public void setAbWeight(String abWeight) {
		this.abWeight = abWeight;
	}

	public String getAbColor() {
		return abColor;
	}

	public void setAbColor(String abColor) {
		this.abColor = abColor;
	}

	public String getAbRescued() {
		return abRescued;
	}

	public void setAbRescued(String abRescued) {
		this.abRescued = abRescued;
	}

	public String getAbCharacter() {
		return abCharacter;
	}

	public void setAbCharacter(String abCharacter) {
		this.abCharacter = abCharacter;
	}

	@Override
	public String toString() {
		return "ABDTO [abID=" + abID + ", shID=" + shID + ", abDate=" + abDate + ", abLocation=" + abLocation
				+ ", abStatus=" + abStatus + ", abBreed=" + abBreed + ", abGender=" + abGender + ", abAge=" + abAge
				+ ", abWeight=" + abWeight + ", abColor=" + abColor + ", abRescued=" + abRescued + ", abCharacter="
				+ abCharacter + "]";
	}
	
	
	

}
