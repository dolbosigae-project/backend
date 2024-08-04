package com.gae.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.HODTO;
import com.gae.service.HOService;
import com.gae.vo.HOResponseVo;

@RestController
@CrossOrigin(origins = "https://dolbosigae.site", allowedHeaders = "*")
public class HOController {

    private final HOService hoService;

    public HOController(HOService hoService) {
        this.hoService = hoService;
    }

    @GetMapping("/hospitals/list")
    public Map<String, Object> selectHoList(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "5") int limit,
        @RequestParam(required = false) String hoText) {

        Map<String, Object> map = new HashMap<>();
        try {
            HOResponseVo response;
            if (hoText == null || hoText.isEmpty()) { 
                response = hoService.getHoList(page, limit);
            } else {
                response = hoService.searchHo(hoText, page, limit); 
            }

            map.put("contents", response.getContents());
            map.put("pagination", response.getPagination());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "Internal Server Error");
        }
        return map;
    }
    
    @GetMapping("/hoinfo")
    public HODTO selectHoInfo(@RequestParam int hoId) {
        System.out.println(hoId);
        HODTO result = hoService.selectHoInfo(hoId);
        System.out.println(result);
        return result;
    }
    
    @DeleteMapping("/hospitals/delete/{hoId}")
    public Map<String, String> deleteHospitals(
            @PathVariable int hoId, 
            @RequestHeader(value = "userRole", defaultValue = "") String userRole) {

        Map<String, String> response = new HashMap<>();
        if (!"ADMIN".equals(userRole)) {
            response.put("status", "error");
            response.put("message", "Unauthorized");
            return response;
        }
        try {
            hoService.deleteHo(hoId);
            response.put("status", "success");
            response.put("message", "Hospital deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Internal Server Error");
        }
        return response;
    }
    
    @PostMapping("/hospitals")
    public String addHospital(@RequestBody HODTO hoDTO) {
        try {
            hoService.insertHo(hoDTO);
            return "Hospital added successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error adding hospital";
        }
    }

    
    
}