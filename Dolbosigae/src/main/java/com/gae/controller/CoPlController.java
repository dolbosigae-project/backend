package com.gae.controller;

import org.springframework.stereotype.Controller;

import com.gae.service.CoService;
import com.gae.service.PlService;

@Controller
public class CoPlController {
	private CoService coService;
	private PlService plService;
	
	public CoPlController(CoService coService, PlService plService) {
		this.coService = coService;
		this.plService = plService;
	}
	
	
}
