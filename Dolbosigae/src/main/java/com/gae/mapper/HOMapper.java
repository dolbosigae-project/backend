package com.gae.mapper;

import com.gae.dto.HODTO;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HOMapper {
	   List<HODTO> getHoList(@Param("startRow") int startRow, @Param("endRow") int endRow);
	    int getTotalCount();
	    HODTO selectHoInfo(@Param("hoId") int hoId);
	    List<HODTO> searchHo(@Param("hoText") String hoText, @Param("startRow") int startRow, @Param("endRow") int endRow);
	    int getTotalCountBySearch(@Param("hoText") String hoText); // 추가된 메서드
		int deleteHo(int hoId);
	}