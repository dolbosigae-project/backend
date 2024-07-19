package com.gae.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.HODTO;
import com.gae.service.HOService;
import com.gae.vo.HOResponseVo;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class HOController {

    private final HOService hoService;

    public HOController(HOService hoService) {
        this.hoService = hoService;
    }

    @GetMapping("/hospitals/list")
    public Map<String, Object> selectHoList(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "5") int limit,
        @RequestParam(required = false) String plText) {
        
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println("Fetching data with params - page: " + page + ", limit: " + limit + ", plText: " + plText);
            HOResponseVo response;
            if (plText == null || plText.isEmpty()) { // hoText를 plText로 변경
                response = hoService.getHoList(page, limit);
            } else {
                response = hoService.searchHo(plText, page, limit); // hoText를 plText로 변경
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

    @DeleteMapping("/hospitals/delete/{hoId}")
    public Map<String, String> deleteHospitals(@PathVariable int hoId) {
        Map<String, String> response = new HashMap<>();
        try {
            hoService.deleteHo(hoId);
            response.put("status", "success");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Internal Server Error");
        }
        return response;
    }
}
