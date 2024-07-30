package com.gae.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.ABDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ABMapper {
    List<ABDTO> selectABList(@Param("startRow") int startRow, @Param("endRow") int endRow);
    List<ABDTO> selectFilteredABList(@Param("startRow") int startRow, @Param("endRow") int endRow, @Param("filterParams") Map<String, Object> filterParams);
    int selectABTotalCount(@Param("filterParams") Map<String, Object> filterParams);
    void insertAB(@Param("abDTO") ABDTO abDTO);
    void deleteAB(@Param("id") String id);
    ABDTO selectABDetail(@Param("id") String id);
}
