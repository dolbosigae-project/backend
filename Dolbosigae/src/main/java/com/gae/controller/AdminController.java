package com.gae.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.gae.service.AdminService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AdminController {
	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	
	
	
}
