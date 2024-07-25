package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;

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
	int deleteAdmin(@Param("adminNo") int adminNo);
	int deleteAllComment(@Param("adminNo") int adminNo);
	int adminCommentDelete(@Param("adminCommentNo") int adminCommentNo);
	List<Admin> searchAdminTitle(@Param("term") String term);
	List<Admin> searchAdminContent(@Param("term") String term);
	List<Admin> searchAdminWriter(@Param("term") String term);
	List<AdminDTO> noAnswerOnly();

}
