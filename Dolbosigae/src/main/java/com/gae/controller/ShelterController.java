package com.gae.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.gae.dto.ShelterDTO;
import com.gae.service.ShelterService;
import com.gae.vo.ShelterResponseVo;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://nam3324.synology.me:32800"})
public class ShelterController {

    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @GetMapping("/shelters/list")
    public Map<String, Object> selectShelterList(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(required = false) String region,
        @RequestParam(required = false) String centerName) {

        Map<String, Object> map = new HashMap<>();
        try {
            ShelterResponseVo response;
            if ((region == null || region.isEmpty()) && (centerName == null || centerName.isEmpty())) { 
                response = shelterService.getShelterList(page, limit);
            } else {
                response = shelterService.searchShelter(region, centerName, page, limit); 
            }

            map.put("contents", response.getContents());
            map.put("pagination", response.getPagination());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "Internal Server Error");
        }
        return map;
    }
    
    @GetMapping("/shelterdetail")
    public ShelterDTO selectShelterInfo(@RequestParam String id) {
        System.out.println(id);
        ShelterDTO result = shelterService.selectShelterInfo(id);
        System.out.println(result);
        return result;
    }
    
    @DeleteMapping("/shelters/delete/{id}")
    public Map<String, String> deleteShelter(
            @PathVariable String id, 
            @RequestHeader(value = "userRole", defaultValue = "") String userRole) {

        Map<String, String> response = new HashMap<>();
        if (!"ADMIN".equals(userRole)) {
            response.put("status", "error");
            response.put("message", "Unauthorized");
            return response;
        }
        try {
            shelterService.deleteShelter(id);
            response.put("status", "success");
            response.put("message", "Shelter deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Internal Server Error");
        }
        return response;
    }
    
    @PostMapping("/shelters")
    public String addShelter(@RequestBody ShelterDTO shelterDTO) {
        try {
            shelterService.insertShelter(shelterDTO);
            return "Shelter added successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error adding shelter";
        }
    }
}
