package com.gae.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.CoDTO;
import com.gae.dto.PlDTO;
import com.gae.service.CoService;
import com.gae.vo.CoResponseVo;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CoController {
	private CoService coService;

	public CoController(CoService coService) {
		this.coService = coService;
	}
	
	@GetMapping("/conven/list")
	public Map<String, Object> convenList(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam(required = false) String coText) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		CoResponseVo response;
		try {
			if(coText == null || coText.isEmpty()) {
				response = coService.getConvenList(page, limit);
			}else {
				response = coService.searchConven(page, limit, coText);
			}
			map.put("contents", response.getContents());
			System.out.println("Pagination info : " + response.getPagination());
			map.put("pagination", response.getPagination());
		}catch(Exception e){
			e.printStackTrace();
			map.put("error", "서버에러");
		}
		return map;
	}
	
	@GetMapping("/conven/info")
	public CoDTO selectConvenInfo(@RequestParam int coId) {
		System.out.println(coId);
		CoDTO result = coService.selectConvenInfo(coId);
		System.out.println(result);
		return result;
	}
	
	 @DeleteMapping("/conven/delete/{coId}")
	    public Map<String, String> deleteCity(@PathVariable int coId) {
	        Map<String, String> response = new HashMap<>();
	        
	        coService.deleteConvenience(coId);
	        try {
	            response.put("status", "success");
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.put("status", "error");
	            response.put("message", "Internal Server Error");
	        }
	        return response;
	    }
	 
	 @PostMapping("/conven/insert")
	    public ResponseEntity<String> convenInsert(@RequestBody CoDTO dto){
	    	coService.convenInsert(dto);
	    	return ResponseEntity.ok("편의시설 추가 완료");
	    }
}