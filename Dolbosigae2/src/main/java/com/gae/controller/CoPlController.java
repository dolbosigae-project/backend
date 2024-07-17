package com.gae.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gae.service.PlService;
import com.gae.vo.PlResponseVo;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CoPlController {

    private final PlService plService;

    public CoPlController(PlService plService) {
        this.plService = plService;
    }

    @GetMapping("/city/list")
    @ResponseBody
    public ResponseEntity<PlResponseVo> selectCityList(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(plService.getCityList(page));
    }
    
}
    