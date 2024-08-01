package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gae.dto.ShelterDTO;

@Mapper
public interface ShelterMapper {

	int getTotalCount();
	List<ShelterDTO> getShelterList(int startRow, int endRow);
	List<ShelterDTO> searchByAllRegion(String term);
	List<ShelterDTO> searchByRegionName(String category, String term);
	ShelterDTO getShelterDetail(String shelterId);
	
}
