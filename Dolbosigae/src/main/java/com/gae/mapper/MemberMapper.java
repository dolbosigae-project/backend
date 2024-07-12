package com.gae.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.BoardMemberDTO;

@Mapper
public interface MemberMapper {

	BoardMemberDTO login(@Param("id") String id, @Param("pass") String pass);

}
