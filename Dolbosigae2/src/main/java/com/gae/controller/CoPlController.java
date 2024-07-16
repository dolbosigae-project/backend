package com.gae.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gae.dto.PlDTO;
import com.gae.service.PlService;
import com.gae.vo.PlPageVo;
import com.gae.vo.PlResponseVo;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CoPlController {

    private final PlService plService;

    public CoPlController(PlService plService) {
        this.plService = plService;
    }

    @ResponseBody
    @GetMapping("/city/list")
    public PlResponseVo selectCity(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "5") int limit) {

        List<PlDTO> contents = plService.selectCity(page, limit); // 페이징된 결과 가져오기
        int totalCount = plService.getTotalCount(); // 전체 개수 가져오기

        PlPageVo pagination = new PlPageVo(totalCount, page, limit); // 페이징 정보 생성
        return new PlResponseVo(contents, pagination); // ResponseVo로 결과 반환
    }
    
    @GetMapping("/search/city")
    public ResponseEntity<?> searchCity(@RequestParam String plCity) {
        System.out.println(plCity);
    	List<PlDTO> contents = plService.searchCity(plCity);
        System.out.println(plCity);
        if (contents.isEmpty()) {
        	System.out.println("2");
            return ResponseEntity.notFound().build(); // 데이터가 없는 경우 404 에러 반환
        }else {
        	System.out.println("3");
        }

        return ResponseEntity.ok(contents);
    }
    
    @GetMapping("/search/playground")
    public ResponseEntity<?> searchPlaygroundDetails(
    		@RequestParam String plId,
            @RequestParam String plName,
            @RequestParam String plHour,
            @RequestParam String plTel,
            @RequestParam String plAddress) {
    	System.out.println("3");
        List<PlDTO> contents = plService.searchPlaygroundDetails(plId, plName, plHour, plTel, plAddress);
        System.out.println("4");
        return ResponseEntity.ok(contents);
    }
}