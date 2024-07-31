package com.gae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gae.mapper.ABMapper;

@Service
public class ABService {
	@Autowired
	private ABMapper abMapper;

	public ABService(ABMapper abMapper) {
		this.abMapper = abMapper;
	}

	//전체 리스트 조회	
	public Object getABList(int page) {
		// TODO Auto-generated method stub
		return null;
	}

}
