package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("Member")
public class BoardMemberDTO {
	private String boardMemberId;
	private String boardMemberPasswd;
	private String boardMemberNaMe;
	private String boardMemberRegion;
	private int boardMemberGradeNo;
	private String boardMemberGradeNaMe;
	private boolean boardMemberPetWith;
	private String petId;
	private String boardMemberNick;
	private String petBirth;
	private String petGender;
	private String petSize;
	private double petWeight;
	private boolean petWalkProfile;
	private int petImageNo;
	private String petImagePath;
	
	public BoardMemberDTO() {	}

	public String getBoardMemberId() {
		return boardMemberId;
	}

	public void setBoardMemberId(String boardMemberId) {
		this.boardMemberId = boardMemberId;
	}

	public String getBoardMemberPasswd() {
		return boardMemberPasswd;
	}

	public void setBoardMemberPasswd(String boardMemberPasswd) {
		this.boardMemberPasswd = boardMemberPasswd;
	}

	public String getBoardMemberNaMe() {
		return boardMemberNaMe;
	}

	public void setBoardMemberNaMe(String boardMemberNaMe) {
		this.boardMemberNaMe = boardMemberNaMe;
	}

	public String getBoardMemberRegion() {
		return boardMemberRegion;
	}

	public void setBoardMemberRegion(String boardMemberRegion) {
		this.boardMemberRegion = boardMemberRegion;
	}

	public int getBoardMemberGradeNo() {
		return boardMemberGradeNo;
	}

	public void setBoardMemberGradeNo(int boardMemberGradeNo) {
		this.boardMemberGradeNo = boardMemberGradeNo;
	}

	public String getBoardMemberGradeNaMe() {
		return boardMemberGradeNaMe;
	}

	public void setBoardMemberGradeNaMe(String boardMemberGradeNaMe) {
		this.boardMemberGradeNaMe = boardMemberGradeNaMe;
	}

	public boolean isBoardMemberPetWith() {
		return boardMemberPetWith;
	}

	public void setBoardMemberPetWith(boolean boardMemberPetWith) {
		this.boardMemberPetWith = boardMemberPetWith;
	}

	public String getPetId() {
		return petId;
	}

	public void setPetId(String petId) {
		this.petId = petId;
	}

	public String getBoardMemberNick() {
		return boardMemberNick;
	}

	public void setBoardMemberNick(String boardMemberNick) {
		this.boardMemberNick = boardMemberNick;
	}

	public String getPetBirth() {
		return petBirth;
	}

	public void setPetBirth(String petBirth) {
		this.petBirth = petBirth;
	}

	public String getPetGender() {
		return petGender;
	}

	public void setPetGender(String petGender) {
		this.petGender = petGender;
	}

	public String getPetSize() {
		return petSize;
	}

	public void setPetSize(String petSize) {
		this.petSize = petSize;
	}

	public double getPetWeight() {
		return petWeight;
	}

	public void setPetWeight(double petWeight) {
		this.petWeight = petWeight;
	}

	public boolean isPetWalkProfile() {
		return petWalkProfile;
	}

	public void setPetWalkProfile(boolean petWalkProfile) {
		this.petWalkProfile = petWalkProfile;
	}

	public int getPetImageNo() {
		return petImageNo;
	}

	public void setPetImageNo(int petImageNo) {
		this.petImageNo = petImageNo;
	}

	public String getPetImagePath() {
		return petImagePath;
	}

	public void setPetImagePath(String petImagePath) {
		this.petImagePath = petImagePath;
	}

	@Override
	public String toString() {
		return "BoardMemberDTO [boardMemberId=" + boardMemberId + ", boardMemberPasswd=" + boardMemberPasswd
				+ ", boardMemberNaMe=" + boardMemberNaMe + ", boardMemberRegion=" + boardMemberRegion
				+ ", boardMemberGradeNo=" + boardMemberGradeNo + ", boardMemberGradeNaMe=" + boardMemberGradeNaMe
				+ ", boardMemberPetWith=" + boardMemberPetWith + ", petId=" + petId + ", boardMemberNick="
				+ boardMemberNick + ", petBirth=" + petBirth + ", petGender=" + petGender + ", petSize=" + petSize
				+ ", petWeight=" + petWeight + ", petWalkProfile=" + petWalkProfile + ", petImageNo=" + petImageNo
				+ ", petImagePath=" + petImagePath + "]";
	}
	
}
