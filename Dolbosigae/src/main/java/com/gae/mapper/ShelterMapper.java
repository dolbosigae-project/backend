package com.gae.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.ShelterDTO;

@Mapper
public interface ShelterMapper {
    List<ShelterDTO> selectShelterList(Map<String, Object> map);
    int selectShelterTotalCount();
    void insertShelter(ShelterDTO shelterDTO);
    void deleteShelter(@Param("id") String id);
}
