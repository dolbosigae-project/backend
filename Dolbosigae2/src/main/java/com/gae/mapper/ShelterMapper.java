package com.gae.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gae.dto.ShelterDTO;

@Mapper
public interface ShelterMapper {

	List<ShelterDTO> selectShelterList(Map<String, Object> map);

	int selectShelterTotalCount();

}
