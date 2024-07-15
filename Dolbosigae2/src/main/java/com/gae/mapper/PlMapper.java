package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.PlDTO;

@Mapper
public interface PlMapper {

    List<PlDTO> selectCity(@Param("startRow") int startRow, @Param("endRow") int endRow);

    int getTotalCount();
}
