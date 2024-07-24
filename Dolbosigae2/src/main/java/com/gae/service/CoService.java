package com.gae.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gae.dto.CoDTO;
import com.gae.mapper.CoMapper;
import com.gae.vo.CoPaggingVo;
import com.gae.vo.CoResponseVo;

@Service
public class CoService {
	private CoMapper coMapper;

	public CoService(CoMapper coMapper) {
		this.coMapper = coMapper;
	}

	public CoResponseVo getConvenList(int page, int limit) {
		int startRow = (page - 1) * limit + 1; 
		int endRow = page * limit;
		List<CoDTO> list = coMapper.getConvenList(startRow, endRow);
		System.out.println(list);
		CoPaggingVo pagination = getPagination(page, limit, null);
		return new CoResponseVo(list, pagination);
	}


	public CoResponseVo searchConven(int page, int limit, String coText) {
		int startRow = (page - 1) * limit + 1;
		int endRow = page * limit;
		List<CoDTO> list = coMapper.searchConven(coText, startRow, endRow);
		CoPaggingVo pagination = getPagination(page, limit, coText);
		return new CoResponseVo(list, pagination);
	}
	
	public CoPaggingVo getPagination(int page, int limit, String coText) {
		int totalCount = coText == null ? coMapper.getTotalCount() : coMapper.getTotalCountBySearch(coText);
        System.out.println("Total Count: " + totalCount); 
		return new CoPaggingVo(totalCount, page, limit);
	}

	public CoDTO selectConvenInfo(int coId) {
		return coMapper.selectConvenInfo(coId);
	}

	public void deleteConvenience(int coId) {
		coMapper.deleteConvenience(coId);
	}
	
}
