package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.AdminDTO;

@Mapper
public interface AdminMapper {

	List<AdminDTO> selectAllDefault();
	int getTotalCount();
	List<AdminDTO> getAdminList(@Param("startRow")int startRow, @Param("endRow")int endRow);
	AdminDTO getAdminContactDetail(int adminNo);
	List<AdminDTO> getAdminContactCommentDetail(int adminNo);
	void writeAdmin(AdminDTO admin);
	void writeAdminComment(AdminDTO admin);

}
