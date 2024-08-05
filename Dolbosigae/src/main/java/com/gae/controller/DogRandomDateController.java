package com.gae.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.BoardMemberDTO;
import com.gae.service.DogRandomDateService;

@CrossOrigin(origins = "https://www.dolbosigae.site")
@RestController
public class DogRandomDateController {
	private final DogRandomDateService dogMateService;
	public DogRandomDateController(DogRandomDateService dogMateService) {
		this.dogMateService = dogMateService;
	}

	@PostMapping("/dog/random/date")
	public ResponseEntity<List<BoardMemberDTO>> searchDogMate(@RequestBody BoardMemberDTO dogRequest){
		//System.out.println(dogRequest);
		List<BoardMemberDTO> response = dogMateService.searchDogMate(dogRequest);
		//System.out.println(response);
		return ResponseEntity.ok(response);
	}
}
