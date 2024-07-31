package com.gae.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.service.ShelterService;
import com.gae.vo.ShelterResonseVo;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ShelterController {
	private final ShelterService shelterService;

	public ShelterController(ShelterService shelterService) {
		this.shelterService = shelterService;
	}
	
	@GetMapping("/shelter/list")
	public ResponseEntity<ShelterResonseVo> selectAllShelter(@RequestParam(defaultValue = "1")int page){
		return ResponseEntity.ok(shelterService.getShelterList(page));
	}
	
}
