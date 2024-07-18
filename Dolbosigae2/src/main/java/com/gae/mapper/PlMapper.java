
package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.PlDTO;
import com.gae.dto.PlSearchViewDTO;
import com.gae.vo.PlResponseVo;

@Mapper
public interface PlMapper {

	List<PlSearchViewDTO> getCityList(@Param("startRow") int startRow, @Param("endRow") int endRow);

	int getTotalCount();

	PlDTO selectCityInfo(int plid);

	
}
