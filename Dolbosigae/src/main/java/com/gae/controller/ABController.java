package com.gae.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.ABDTO;
import com.gae.service.ABService;
import com.gae.vo.ABResponseVo;

@CrossOrigin(origins = "*")
@RestController
public class ABController {
	private final ABService abService;

	public ABController(ABService abService) {
		this.abService = abService;
	}
	
	@GetMapping("/healthcheck")
    public String healthcheck() {
        return "OK";
    }
	
	//전체 리스트 조회
	@GetMapping("/ab/list")
	public ResponseEntity<ABResponseVo> selectAllAB(@RequestParam(defaultValue = "1")int page){
		return ResponseEntity.ok(abService.getABList(page));
	}
	
	//검색 결과 조회
	@GetMapping("/ab/search")
	 public ResponseEntity<?> ABSearch(@RequestParam String category, @RequestParam String term) {
       List<ABDTO> searchResults = abService.searchAB(category, term);
       return ResponseEntity.ok(searchResults);
   }	
	
	//특정 강아지 상세
	@GetMapping("/ab/detail/{abid}")
	public ResponseEntity<ABDTO> getABDetail(@PathVariable String abid){
		ABDTO detail = abService.getABDetail(abid);
	    if (detail != null) {
	    	return ResponseEntity.ok(detail);
	    } else {
	    	return ResponseEntity.notFound().build();
	        }
		}
}