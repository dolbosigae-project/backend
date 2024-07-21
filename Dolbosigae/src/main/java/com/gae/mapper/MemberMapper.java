package com.gae.mapper;

import java.lang.reflect.Member;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.BoardMemberDTO;

@Mapper
public interface MemberMapper {

	BoardMemberDTO login(@Param("id") String id, @Param("pass") String pass);
	int getTotalCount();
	List<BoardMemberDTO> getMemberList(@Param("startRow") int startRow, @Param("endRow") int endRow);
	int deleteMember(@Param("id") String id);
	int updateMember(BoardMemberDTO member);
	int updateMemberPassword(BoardMemberDTO member);
	int updatePet(BoardMemberDTO member);
	List<Member> searchByBoardMemberId(@Param("term") String term);
    List<Member> searchByBoardMemberName(@Param("term") String term);
    List<Member> searchByBoardMemberRegion(@Param("term") String term);
    List<Member> searchByBoardMemberGradeName(@Param("term") String term);
    Integer checkDuplicate(String idValue);
	void insertMember(BoardMemberDTO member);
	void insertPet(BoardMemberDTO member);
	void insertDefaultPet(BoardMemberDTO defaultPet);
	void insertPetImg(BoardMemberDTO member);
	int updatePasswd(String boardMemberId, String boardMemberPasswd);
	BoardMemberDTO myPage(String id);
}