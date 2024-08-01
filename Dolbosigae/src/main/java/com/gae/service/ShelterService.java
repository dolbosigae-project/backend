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

	//전체 리스트 조회	
	public ShelterResonseVo getShelterList(int page) {
		int pageOfContentCount = 10; //페이지당 센터 수
		int totalCount = shelterMapper.getTotalCount(); //전체 센터 수 가져오기
		ShelterPaggingVo paggingVo = new ShelterPaggingVo(totalCount, page, pageOfContentCount);
		
		int startRow = (page - 1) * pageOfContentCount;
		int endRow = startRow + pageOfContentCount;
		List<ShelterDTO> shelters = shelterMapper.getShelterList(startRow, endRow);
		
		return new ShelterResonseVo(shelters, paggingVo);
	}

	//검색 결과 조회
	public List<ShelterDTO> searchShelters(String category, String term) {
		if(category.equals("지역선택")) {
			return shelterMapper.searchByAllRegion(term);
		} else {
			return shelterMapper.searchByRegionName(category, term);
		}
    }

	//특정 센터 상세
	public ShelterDTO getShelterDetail(String shelterId) {
		ShelterDTO shelterDetail = shelterMapper.getShelterDetail(shelterId);
		return shelterDetail;
	}
}

	
	
	

