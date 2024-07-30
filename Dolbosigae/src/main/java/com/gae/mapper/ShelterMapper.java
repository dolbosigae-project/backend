package com.gae.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.ShelterDTO;

@Mapper
public interface ShelterMapper {
    List<ShelterDTO> getShelterList(@Param("startRow") int startRow, @Param("endRow") int endRow);
    int getTotalCount();
    ShelterDTO selectShelterInfo(@Param("id") String id);
    List<ShelterDTO> searchShelter(@Param("region") String region, @Param("centerName") String centerName, @Param("startRow") int startRow, @Param("endRow") int endRow);
    int getTotalCountBySearch(@Param("region") String region, @Param("centerName") String centerName);
    int deleteShelter(@Param("id") String id);
    int insertShelter(ShelterDTO shelterDTO);
}
