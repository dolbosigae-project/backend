package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("plSearch")
public class PlSearchViewDTO {
	private String plId;
	private String plName;
	private String plHour;
	private String plTel;
	private String plAddress;
	
	public PlSearchViewDTO() {	}
	
	public PlSearchViewDTO(String plId, String plName, String plHour, String plTel, String plAddress) {
		this.plId = plId;
		this.plName = plName;
		this.plHour = plHour;
		this.plTel = plTel;
		this.plAddress = plAddress;
	}
	
	/**
	 * @return the plId
	 */
	public String getPlId() {
		return plId;
	}
	/**
	 * @param plId the plId to set
	 */
	public void setPlId(String plId) {
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
	
	@Override
	public String toString() {
		return "PlSearchDTO [plId=" + plId + ", plName=" + plName + ", plHour=" + plHour + ", plTel=" + plTel
				+ ", plAddress=" + plAddress + "]";
	}
	
	
}