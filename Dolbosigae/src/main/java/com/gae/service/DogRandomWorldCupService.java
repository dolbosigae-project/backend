package com.gae.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gae.dto.DogRandomWorldCupDTO;
import com.gae.mapper.DogRandomWorldCupMapper;

@Service
public class DogRandomWorldCupService {
	private DogRandomWorldCupMapper DRWCmapper;

	public DogRandomWorldCupService(DogRandomWorldCupMapper dRWCmapper) {
		DRWCmapper = dRWCmapper;
	}

	public List<DogRandomWorldCupDTO> selectRandomDog() {
		return DRWCmapper.selectRandomDog();
	}
	
	
	
}
