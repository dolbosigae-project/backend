package com.gae.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.ShelterDTO;

@Mapper
public interface ShelterMapper {
    List<ShelterDTO> selectShelterList(Map<String, Object> map);
    void insertShelter(ShelterDTO shelterDTO);
    void deleteShelter(@Param("id") String id);
    ShelterDTO selectShelterById(@Param("id") String id);
    int selectShelterTotalCount(Map<String, Object> filterParams);
    List<ShelterDTO> selectAllShelters();
}