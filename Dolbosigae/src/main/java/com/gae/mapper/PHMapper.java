package com.gae.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gae.dto.PHDTO;

@Mapper
public interface PHMapper {

    List<PHDTO> selectPhNewList(Map<String, Object> map);

    int selectPhTotalCount();

    PHDTO selectPh(int pno);
}
