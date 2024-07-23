package com.gae.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gae.dto.ABDTO;

@Mapper
public interface ABMapper {

	List<ABDTO> selectABList(Map<String, Object> map);

	int selectABTotalCount(String shID);

}
