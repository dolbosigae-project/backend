package com.gae.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gae.dto.PlDTO;
import com.gae.dto.PlSearchViewDTO;
import com.gae.mapper.PlMapper;
import com.gae.vo.PlPaggingVo;
import com.gae.vo.PlResponseVo;

@Service
public class PlService {

    private final PlMapper plMapper;

    public PlService(PlMapper plMapper) {
        this.plMapper = plMapper;
    }

    //기본 리스트

	public PlResponseVo getCityList(int page, int limit) {
		int pageOfContentCount = 5;
		int totalCount = plMapper.getTotalCount();
		PlPaggingVo pageVo = new PlPaggingVo(totalCount, page, pageOfContentCount);
		
		int startRow = (page - 1) * pageOfContentCount;
		int endRow = startRow + pageOfContentCount;
		List<PlSearchViewDTO> list = plMapper.getCityList(startRow, endRow);
		return new PlResponseVo(list , pageVo);
	}

	public PlDTO selectCityInfo(int plid) {
		return plMapper.selectCityInfo(plid);
	}

	


   
   
}