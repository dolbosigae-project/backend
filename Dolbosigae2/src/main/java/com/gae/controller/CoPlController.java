package com.gae.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.service.MemberService;
import com.gae.service.PlService;
import com.gae.dto.BoardMemberDTO;
import com.gae.dto.PlDTO;
import com.gae.vo.PlResponseVo;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CoPlController {

    private final PlService plService;
    private final MemberService memberService;
    
    public CoPlController(PlService plService, MemberService memberService) {
        this.plService = plService;
        this.memberService = memberService;
    }

    //게시물 기본 리스트
    @GetMapping("/city/list")
    public Map<String, Object> selectCityList(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "5") int limit,
        @RequestParam(required = false) String plText) {
        
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println("Fetching data with params - page: " + page + ", limit: " + limit + ", plText: " + plText);
            PlResponseVo response;
            if (plText == null || plText.isEmpty()) {
                response = plService.getCityList(page, limit);
            } else {
                response = plService.searchCity(plText, page, limit);
            }
            map.put("contents", response.getContents());
            map.put("pagination", response.getPagination());
            System.out.println("Pagination info: " + response.getPagination()); // 로그 추가
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "Internal Server Error");
        }
        return map;
    }
    
    //게시물 검색
    @GetMapping("/city/info")
    public PlDTO selectCityInfo(@RequestParam int plId) {
        System.out.println(plId);
        PlDTO result = plService.selectCityInfo(plId);
        System.out.println(result);
        return result;
    }

    //게시물 삭제
    @DeleteMapping("/city/delete/{plId}")
    public Map<String, String> deleteCity(@PathVariable int plId) {
        Map<String, String> response = new HashMap<>();
        
        plService.deleteCity(plId);
        try {
            response.put("status", "success");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Internal Server Error");
        }
        return response;
    }
  
}
