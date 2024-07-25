package com.gae.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gae.dto.BoardMemberDTO;

@Mapper
public interface DogRandomDateMapper {

	void searchDogMate(BoardMemberDTO dogRequest);

}
