package com.gae.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gae.dto.ABDTO;
import com.gae.mapper.ABMapper;
import com.gae.vo.ABPaggingVo;
import com.gae.vo.ABResponseVo;

@Service
public class ABService {
	@Autowired
	private ABMapper abMapper;

	public ABService(ABMapper abMapper) {
		this.abMapper = abMapper;
	}

	//전체 리스트 조회	
	public ABResponseVo getABList(int page) {
		int pageOfContentCount = 6; //페이지당 강아지 수
		int totalCount = abMapper.getTotalCount(); //전체 센터 수 가져오기
		ABPaggingVo paggingVo = new ABPaggingVo(totalCount, page, pageOfContentCount);
		
		int startRow = (page - 1) * pageOfContentCount;
		int endRow = startRow + pageOfContentCount;
		List<ABDTO> abs = abMapper.getABList(startRow, endRow);
		
		return new ABResponseVo(abs, paggingVo);
	}
	
	//검색 결과 조회
	public List<ABDTO> searchAB(String category, String term) {
		if(category.equals("지역선택")) {
			return abMapper.searchByAllRegion(term);
		} else {
			return abMapper.searchByBreedName(category, term);
		}
    }

	//특정 강아지 상세
	public ABDTO getABDetail(String abid) {
		ABDTO ABDetail = abMapper.getABDetail(abid);
		return ABDetail;
	}

}