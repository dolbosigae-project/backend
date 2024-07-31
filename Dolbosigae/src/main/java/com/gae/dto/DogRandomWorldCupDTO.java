package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("drwc")
public class DogRandomWorldCupDTO {
	private int dogId;
	private String dogTypeName;
	private String dogTypeInfo;
	private String dogImg;
	private int dogSize;
	private int dogRanking;
	
	public DogRandomWorldCupDTO() {	}

	public DogRandomWorldCupDTO(int dogId, String dogTypeName, String dogTypeInfo, String dogImg, int dogSize,
			int dogRanking) {
		super();
		this.dogId = dogId;
		this.dogTypeName = dogTypeName;
		this.dogTypeInfo = dogTypeInfo;
		this.dogImg = dogImg;
		this.dogSize = dogSize;
		this.dogRanking = dogRanking;
	}

	/**
	 * @return the dogId
	 */
	public int getDogId() {
		return dogId;
	}

	/**
	 * @param dogId the dogId to set
	 */
	public void setDogId(int dogId) {
		this.dogId = dogId;
	}

	/**
	 * @return the dogTypeName
	 */
	public String getDogTypeName() {
		return dogTypeName;
	}

	/**
	 * @param dogTypeName the dogTypeName to set
	 */
	public void setDogTypeName(String dogTypeName) {
		this.dogTypeName = dogTypeName;
	}

	/**
	 * @return the dogTypeInfo
	 */
	public String getDogTypeInfo() {
		return dogTypeInfo;
	}

	/**
	 * @param dogTypeInfo the dogTypeInfo to set
	 */
	public void setDogTypeInfo(String dogTypeInfo) {
		this.dogTypeInfo = dogTypeInfo;
	}

	/**
	 * @return the dogImg
	 */
	public String getDogImg() {
		return dogImg;
	}

	/**
	 * @param dogImg the dogImg to set
	 */
	public void setDogImg(String dogImg) {
		this.dogImg = dogImg;
	}

	/**
	 * @return the dogSize
	 */
	public int getDogSize() {
		return dogSize;
	}

	/**
	 * @param dogSize the dogSize to set
	 */
	public void setDogSize(int dogSize) {
		this.dogSize = dogSize;
	}

	/**
	 * @return the dogRanking
	 */
	public int getDogRanking() {
		return dogRanking;
	}

	/**
	 * @param dogRanking the dogRanking to set
	 */
	public void setDogRanking(int dogRanking) {
		this.dogRanking = dogRanking;
	}

	@Override
	public String toString() {
		return "DogRandomWorldCupDTO [dogId=" + dogId + ", dogTypeName=" + dogTypeName + ", dogTypeInfo=" + dogTypeInfo
				+ ", dogImg=" + dogImg + ", dogSize=" + dogSize + ", dogRanking=" + dogRanking + "]";
	}
	
	
	
}