package com.gae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gae.mapper.AdminMapper;

@Service
public class AdminService {
	@Autowired
	private AdminMapper adminMapper;

	public AdminService(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}
	
	

}
