package com.gae.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.PlDTO;
import com.gae.service.PlService;
import com.gae.vo.PlResponseVo;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CoPlController {

    private final PlService plService;

    public CoPlController(PlService plService) {
        this.plService = plService;
    }

    //화면을 켰을 때 frontend로 처음으로 한번 보내주는 컨트롤러
    @ResponseBody
    @GetMapping("/city/list")
    public ResponseEntity<PlResponseVo> selectCityList(
    		@RequestParam(defaultValue = "1") int page,
    		@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(plService.getCityList(page, limit));
    }
    
    //도시 검색 컨트롤러
    @ResponseBody
    @GetMapping("/search/city")
    public ResponseEntity<PlResponseVo> searchCityList(){
    	return null;
    }
    
    @ResponseBody
    @GetMapping("/city/info")
    public PlDTO selectCityInfo(int plId){
    	System.out.println(plId);
    	
    	PlDTO dto = plService.selectCityInfo(plId);
    	System.out.println(dto);
    	
    	
    	return dto;
    }
    
}
    