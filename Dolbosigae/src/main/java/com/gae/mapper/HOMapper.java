package com.gae.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gae.dto.HODTO;

@Mapper
public interface HOMapper {

	List<HODTO> selectHoNewList(Map<String, Object> map);
	int selectHoTotalCount();
	HODTO selectHO(int hno);
}
