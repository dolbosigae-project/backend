package com.gae.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.gae.dto.PlDTO;

import java.util.List;

@Mapper
public interface PlMapper {
    List<PlDTO> getCityList(@Param("startRow") int startRow, @Param("endRow") int endRow);
    int getTotalCount();
    PlDTO selectCityInfo(@Param("plId") int plId);
    List<PlDTO> searchCity(@Param("plText") String plText, @Param("startRow") int startRow, @Param("endRow") int endRow);
    int getTotalCountBySearch(@Param("plText") String plText); // 추가된 메서드
	int deleteCity(int plId);
}