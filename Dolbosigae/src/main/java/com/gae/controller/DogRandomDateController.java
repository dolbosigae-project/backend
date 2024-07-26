package com.gae.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.BoardMemberDTO;
import com.gae.service.DogRandomDateService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class DogRandomDateController {
	private final DogRandomDateService dogMateService;
	public DogRandomDateController(DogRandomDateService dogMateService) {
		this.dogMateService = dogMateService;
	}

	@GetMapping("/dog/random/date")
	public ResponseEntity<String> searchDogMate(@RequestBody BoardMemberDTO dogRequest){
		//System.out.println(dogRequest);
		dogMateService.searchDogMate(dogRequest);
		return ResponseEntity.ok("정보 조회 성공");
	}
}
