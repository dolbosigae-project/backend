package com.gae.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gae.dto.AdminDTO;
import com.gae.mapper.AdminMapper;

@Service
public class AdminService {
	@Autowired
	private AdminMapper adminMapper;

	public AdminService(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}

	//자주 묻는 질문	
	public List<AdminDTO> selectAllDefault() {
		return adminMapper.selectAllDefault();
	}

	
	

}
