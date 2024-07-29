package com.gae.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.AdminDTO;
import com.gae.dto.ShowDTO;
import com.gae.service.ShowService;
import com.gae.vo.HOResponseVo;
import com.gae.vo.ShowResponseVo;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    
    
    @GetMapping("/boards/list")
    public Map<String, Object> selectHoList(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "5") int limit,
        @RequestParam(required = false) String showText) {

        Map<String, Object> map = new HashMap<>();
        try {
            ShowResponseVo response;
            if (showText == null || showText.isEmpty()) { 
                response = showService.getShowList(page, limit);
            } else {
                response = showService.searchShow(showText, page, limit); 
            }

            map.put("contents", response.getContents());
            map.put("pagination", response.getPagination());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "Internal Server Error");
        }
        return map;
    }
    
    

    @GetMapping("/showinfo")
    public ShowDTO selectShowInfo(@RequestParam int showNo) {
        try {
            ShowDTO result = showService.selectShowInfo(showNo);
            if (result != null) {
                return result;
            } else {
                throw new RuntimeException("Show not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving show info");
        }
    }
    
    @DeleteMapping("/boards/delete/{showNo}")
    public Map<String, String> deleteShow(
            @PathVariable int showNo, 
            @RequestHeader(value = "userRole", defaultValue = "") String userRole) {

        Map<String, String> response = new HashMap<>();
        if (!"ADMIN".equals(userRole)) {
            response.put("status", "error");
            response.put("message", "Unauthorized");
            return response;
        }
        try {
            showService.deleteShow(showNo);
            response.put("status", "success");
            response.put("message", "Show deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Internal Server Error");
        }
        return response;
    }
    
    @PostMapping("/shows")
    public Map<String, String> addShow(@RequestBody ShowDTO showDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            showService.insertShow(showDTO);
            response.put("status", "success");
            response.put("message", "Show added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error adding show");
        }
        return response;
    }
    // 일반 문의 게시판 : 글쓰기
    @PostMapping("/board/write")
    public ResponseEntity<String> writeBoard(@RequestBody ShowDTO showDTO){
    	System.out.println(showDTO);
    	showService.writeBoard(showDTO);
    	return ResponseEntity.ok("회원가입 성공");
    }
    
}
