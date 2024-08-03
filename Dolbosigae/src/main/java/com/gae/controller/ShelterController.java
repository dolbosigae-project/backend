package com.gae.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.ShelterDTO;
import com.gae.service.ShelterService;
import com.gae.vo.ShelterResonseVo;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ShelterController {
	private final ShelterService shelterService;

	public ShelterController(ShelterService shelterService) {
		this.shelterService = shelterService;
	}
	
	//전체 리스트 조회	
	@GetMapping("/shelter/list")
	public ResponseEntity<ShelterResonseVo> selectAllShelter(@RequestParam(defaultValue = "1")int page){
		return ResponseEntity.ok(shelterService.getShelterList(page));
	}
	
	//검색 결과 조회
	@GetMapping("/shelter/search")
	 public ResponseEntity<?> shelterSearch(@RequestParam String category, @RequestParam String term) {
        List<ShelterDTO> searchResults = shelterService.searchShelters(category, term);
        return ResponseEntity.ok(searchResults);
    }
	
	//특정 센터 상세
	@GetMapping("/shelter/detail/{shelterId}")
	public ResponseEntity<ShelterDTO> getShelterDetail(@PathVariable String shelterId){
		ShelterDTO detail = shelterService.getShelterDetail(shelterId);
        if (detail != null) {
            return ResponseEntity.ok(detail);
        } else {
            return ResponseEntity.notFound().build();
        }
	}
	
}