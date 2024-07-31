package com.gae.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gae.dto.DogRandomWorldCupDTO;
import com.gae.service.DogRandomWorldCupService;

import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class DogRandomWorldCupController {

    private final DogRandomWorldCupService DRWCservice;

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

    @GetMapping("/winnerDog")
    public DogRandomWorldCupDTO WinnerDogView(@RequestParam int dogId) {
        return DRWCservice.selectWinnerDog(dogId);
    }

    @PostMapping("/winCount")
    public ResponseEntity<String> updateWinCount(@RequestParam int dogId) {
        try {
            DRWCservice.incrementWinCount(dogId);
            return ResponseEntity.ok("Win count updated");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating win count");
        }
    }
    
    @GetMapping("/AllRanking")
    public List<DogRandomWorldCupDTO> AllRanking(){
    	List<DogRandomWorldCupDTO> rank = DRWCservice.selectAllRanking();
    	return rank;
    }
 
    
}






