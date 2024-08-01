package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gae.dto.ABDTO;

@Mapper
public interface ABMapper {

	int getTotalCount();
	List<ABDTO> getABList(int startRow, int endRow);
	ABDTO getABDetail(String abid);
	List<ABDTO> searchByAllRegion(String term);
	List<ABDTO> searchByBreedName(String category, String term);

}
