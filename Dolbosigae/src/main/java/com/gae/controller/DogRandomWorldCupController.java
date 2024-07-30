package com.gae.controller;

import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gae.dto.DogRandomWorldCupDTO;
import com.gae.service.DogRandomDateService;
import com.gae.service.DogRandomWorldCupService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class DogRandomWorldCupController {
	
	private DogRandomWorldCupService DRWCservice;

	public DogRandomWorldCupController(DogRandomWorldCupService dRWCservice) {
		DRWCservice = dRWCservice;
	}
	
	@GetMapping("/RandomDog")
	public List<DogRandomWorldCupDTO> DogRandomView() {
		List<DogRandomWorldCupDTO> dogs = DRWCservice.selectRandomDog();
		Random random = new Random();
		for (int i = 0; i < dogs.size(); i++) {
			int randomIndex = random.nextInt(dogs.size());
			DogRandomWorldCupDTO temp = dogs.get(i);
			dogs.set(i, dogs.get(randomIndex));
			dogs.set(randomIndex, temp);
		}
		return dogs;
	}
	
}
