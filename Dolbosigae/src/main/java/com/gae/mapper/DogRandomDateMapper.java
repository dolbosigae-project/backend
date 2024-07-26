package com.gae.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gae.dto.BoardMemberDTO;

@Mapper
public interface DogRandomDateMapper {

	List<BoardMemberDTO> searchDogMateByAllConditions(Map<String, Object> paramMap);
	List<BoardMemberDTO> searchDogMateByWalkProfileSizeAndGender(Map<String, Object> paramMap);
	List<BoardMemberDTO> searchDogMateByWalkProfileSizeAndAgeRange(Map<String, Object> paramMap);
	List<BoardMemberDTO> searchDogMateByWalkProfileGenderAndAgeRange(Map<String, Object> paramMap);
	List<BoardMemberDTO> searchDogMateByWalkProfileAndSize(Map<String, Object> paramMap);
	List<BoardMemberDTO> searchDogMateByWalkProfileAndGender(Map<String, Object> paramMap);
	List<BoardMemberDTO> searchDogMateByWalkProfileAndAgeRange(Map<String, Object> paramMap);
	List<BoardMemberDTO> searchDogMateByWalkProfile(Map<String, Object> paramMap);


}
