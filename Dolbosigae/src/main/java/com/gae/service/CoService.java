package com.gae.service;

import org.springframework.stereotype.Service;

import com.gae.mapper.CoMapper;

@Service
public class CoService {
	private CoMapper mapper;

	public CoService(CoMapper mapper) {
		this.mapper = mapper;
	}
	
	
}
