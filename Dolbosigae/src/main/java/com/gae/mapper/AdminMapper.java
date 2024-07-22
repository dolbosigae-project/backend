package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gae.dto.AdminDTO;

@Mapper
public interface AdminMapper {

	List<AdminDTO> selectAllDefault();

}
