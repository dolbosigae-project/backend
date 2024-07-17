
package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.PlSearchViewDTO;

@Mapper
public interface PlMapper {

    int getTotalCount();

	List<PlSearchViewDTO> getCityList(@Param("startRow") int startRow, @Param("endRow") int endRow);

	
}
