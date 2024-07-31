package com.gae.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gae.dto.ShelterDTO;
import com.gae.mapper.ShelterMapper;
import com.gae.vo.ShelterPaggingVo;
import com.gae.vo.ShelterResonseVo;

@Service
public class ShelterService {
	@Autowired
	private ShelterMapper shelterMapper;

	public ShelterService(ShelterMapper shelterMapper) {
		this.shelterMapper = shelterMapper;
	}

	public ShelterResonseVo getShelterList(int page) {
		int pageOfContentCount = 10; //페이지당 센터 수
		int totalCount = shelterMapper.getTotalCount(); //전체 센터 수 가져오기
		ShelterPaggingVo paggingVo = new ShelterPaggingVo(totalCount, page, pageOfContentCount);
		
		int startRow = (page - 1) * pageOfContentCount;
		int endRow = startRow + pageOfContentCount;
		List<ShelterDTO> shelters = shelterMapper.getShelterList(startRow, endRow);
		
		return new ShelterResonseVo(shelters, paggingVo);
	}

	
	
	
}
