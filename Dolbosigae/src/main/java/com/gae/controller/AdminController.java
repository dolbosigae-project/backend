package com.gae.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.AdminDTO;
import com.gae.service.AdminService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AdminController {
	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	//자주 묻는 질문
	@GetMapping("/admin/default")
	public List<AdminDTO> selectAllDefault(){
		return adminService.selectAllDefault();
	}
	
}
