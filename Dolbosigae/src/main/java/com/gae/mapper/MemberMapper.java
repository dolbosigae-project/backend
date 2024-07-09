package com.gae.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gae.dto.BoardMemberDTO;

@Mapper
public interface MemberMapper {

	BoardMemberDTO login(Map<String, Object> map);

}
