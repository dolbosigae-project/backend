package com.gae.controller;

import org.springframework.stereotype.Controller;

import com.gae.service.CoService;

@Controller
public class CoController {
	private CoService coService;

	public CoController(CoService coService) {
		this.coService = coService;
	}
	
	
	
	
}
