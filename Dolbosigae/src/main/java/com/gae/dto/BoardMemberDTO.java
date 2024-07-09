package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("member")
public class BoardMemberDTO {
	private String boardMemberId;
	private String boardMemberName;
	private String boardMemberPasswd;
	private int patNo;

	public BoardMemberDTO() {
	}

	public BoardMemberDTO(String boardMemberId, String boardMemberName, String boardMemberPasswd, int patNo) {
		this.boardMemberId = boardMemberId;
		this.boardMemberName = boardMemberName;
		this.boardMemberPasswd = boardMemberPasswd;
		this.patNo = patNo;
	}

	public String getBoardMemberId() {
		return boardMemberId;
	}

	public void setBoardMemberId(String boardMemberId) {
		this.boardMemberId = boardMemberId;
	}

	public String getBoardMemberName() {
		return boardMemberName;
	}

	public void setBoardMemberName(String boardMemberName) {
		this.boardMemberName = boardMemberName;
	}

	public String getBoardMemberPasswd() {
		return boardMemberPasswd;
	}

	public void setBoardMemberPasswd(String boardMemberPasswd) {
		this.boardMemberPasswd = boardMemberPasswd;
	}

	public int getPatNo() {
		return patNo;
	}

	public void setPatNo(int patNo) {
		this.patNo = patNo;
	}

	@Override
	public String toString() {
		return "BoardMemberDTO [boardMemberId=" + boardMemberId + ", boardMemberName=" + boardMemberName
				+ ", boardMemberPasswd=" + boardMemberPasswd + ", patNo=" + patNo + "]";
	}
	
}
