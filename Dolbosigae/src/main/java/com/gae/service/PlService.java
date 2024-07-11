package com.gae.service;

import org.springframework.stereotype.Service;

import com.gae.mapper.PlMapper;

@Service
public class PlService {
	private PlMapper mapper;

	public PlService(PlMapper mapper) {
		this.mapper = mapper;
	}
	
	
}
