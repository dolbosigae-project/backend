package com.gae.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.ABDTO;

@Mapper
public interface ABMapper {
    List<ABDTO> selectABList(Map<String, Object> map);
    List<ABDTO> selectFilteredABList(Map<String, Object> map);
    int selectABTotalCount(Map<String, Object> filterParams);
    void insertAB(@Param("abDTO") ABDTO abDTO);
    void deleteAB(@Param("id") String id);
    ABDTO selectABDetail(@Param("id") String id);
}

