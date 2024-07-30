package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.gae.dto.DogRandomWorldCupDTO;

@Mapper
public interface DogRandomWorldCupMapper {
	List<DogRandomWorldCupDTO> selectRandomDog();
	
}
