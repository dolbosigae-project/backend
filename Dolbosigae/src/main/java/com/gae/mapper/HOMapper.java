package com.gae.mapper;

import com.gae.dto.HODTO;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HOMapper {
	List<HODTO> selectHo();  // 모든 HO를 선택
    List<HODTO> getHoList(Map<String, Object> map);  // 페이징된 HO 리스트 선택
    HODTO searchByHoId(@Param("term") String term); 
    List<String> selectRegion();  // 지역 리스트 선택
}