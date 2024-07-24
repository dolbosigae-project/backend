package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.CoDTO;
import com.gae.vo.CoPaggingVo;

@Mapper
public interface CoMapper {

	List<CoDTO> getConvenList(@Param("startRow") int startRow,@Param("endRow")int endRow);
	int getTotalCount();
	List<CoDTO> searchConven(@Param("coText") String coText,@Param("startRow") int startRow,@Param("endRow") int endRow);
	int getTotalCountBySearch(@Param("coText") String coText);
	CoDTO selectConvenInfo(int coId);
	int deleteConvenience(int coId);

}
