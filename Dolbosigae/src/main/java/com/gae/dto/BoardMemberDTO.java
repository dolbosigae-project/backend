package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("Member")
public class BoardMemberDTO {
    private String boardMemberId; 
    private String boardMemberPasswd; 
    private String boardMemberName; 
    private String boardMemberRegion; 
    private int boardMemberGradeNo; 
    private String boardMemberGradeName; 
    private char boardMemberPetWith; 
    private String mateFav;
    private String petId; 
    private String boardMemberNick; 
    private String petBirth; 
    private char petGender; 
    private String petSize; 
    private double petWeight; 
    private char petWalkProfile; 
    private int petImageNo; 
    private String petImagePath; 
    private String petInfo;
    private String pId; 
    private String profileImg;
    
    //산책친구 시, 구
    private String city;
    private String district;
    //즐겨찾기 - 현재 로그인, 대상
    private String loginId;  
    private String targetId; 
    
    //마찬가지로 산책친구 시, 구의 get,set
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    
    //즐찾
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	//xml에 추가한 favorite에 대한 프로퍼티
	public String getMateFav() {
		return mateFav;
	}
	public void setMateFav(String mateFav) {
		this.mateFav = mateFav;
	}
	
	public String getpId() {
		return pId;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
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
	public String getBoardMemberName() {
		return boardMemberName;
	}
	public void setBoardMemberName(String boardMemberName) {
		this.boardMemberName = boardMemberName;
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
	public String getBoardMemberGradeName() {
		return boardMemberGradeName;
	}
	public void setBoardMemberGradeName(String boardMemberGradeName) {
		this.boardMemberGradeName = boardMemberGradeName;
	}
	public char getBoardMemberPetWith() {
		return boardMemberPetWith;
	}
	public void setBoardMemberPetWith(char boardMemberPetWith) {
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
	public char getPetGender() {
		return petGender;
	}
	public void setPetGender(char petGender) {
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
	public char getPetWalkProfile() {
		return petWalkProfile;
	}
	public void setPetWalkProfile(char petWalkProfile) {
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
	public String getPetInfo() {
		return petInfo;
	}
	public void setPetInfo(String petInfo) {
		this.petInfo = petInfo;
	}
	
	@Override
	public String toString() {
		return "BoardMemberDTO [boardMemberId=" + boardMemberId + ", boardMemberPasswd=" + boardMemberPasswd
				+ ", boardMemberName=" + boardMemberName + ", boardMemberRegion=" + boardMemberRegion
				+ ", boardMemberGradeNo=" + boardMemberGradeNo + ", boardMemberGradeName=" + boardMemberGradeName
				+ ", boardMemberPetWith=" + boardMemberPetWith + ", petId=" + petId + ", boardMemberNick="
				+ boardMemberNick + ", petBirth=" + petBirth + ", petGender=" + petGender + ", petSize=" + petSize
				+ ", petWeight=" + petWeight + ", petWalkProfile=" + petWalkProfile + ", petImageNo=" + petImageNo
				+ ", petImagePath=" + petImagePath + ", petInfo=" + petInfo + ", pId=" + pId + ", profileImg="
				+ profileImg + "]";
	}
	
	
}