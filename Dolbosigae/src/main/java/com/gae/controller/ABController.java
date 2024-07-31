package com.gae.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.service.ABService;
import com.gae.vo.ABResponseVo;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ABController {
	private final ABService abService;

	public ABController(ABService abService) {
		this.abService = abService;
	}
	
	//전체 리스트 조회
	@GetMapping("/ab/list")
	public ResponseEntity<ABResponseVo> selectAllShelter(@RequestParam(defaultValue = "1")int page){
		return ResponseEntity.ok(abService.getABList(page));
	}
	
}
