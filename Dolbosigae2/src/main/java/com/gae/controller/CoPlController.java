package com.gae.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gae.dto.PlDTO;
import com.gae.service.CoService;
import com.gae.service.PlService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CoPlController {
	private CoService coService;
	private PlService plService;
	
	public CoPlController(CoService coService, PlService plService) {
		this.coService = coService;
		this.plService = plService;
	}
	
	@PostMapping("/search/city")
	public ModelAndView searchBtnClick(ModelAndView view, HttpSession session,
			@RequestParam String plCity ) {
		PlDTO plDTO = (PlDTO) session.getAttribute("info");
//		List<PlDTO> list = plService.selectCity();
		return view;
	}
	
}
