package com.gae.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.gae.dto.ShelterDTO;
import com.gae.service.ShelterService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://nam3324.synology.me:32800"})
public class ShelterController {

    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    // 전체 조회
    @GetMapping("/shelters")
    public List<ShelterDTO> getAllShelters() {
        return shelterService.getAllShelters();
    }

    // 부분 조회 (필터링 및 페이징)
    @GetMapping("/shelters/search")
    public Map<String, Object> searchShelters(
        @RequestParam(defaultValue = "1") int pageNo,
        @RequestParam(defaultValue = "10") int pageContentEa,
        @RequestParam(required = false) String region,
        @RequestParam(required = false) String centerName) {

        Map<String, Object> filterParams = new HashMap<>();
        if (region != null && !region.isEmpty()) {
            filterParams.put("region", region);
        }
        if (centerName != null && !centerName.isEmpty()) {
            filterParams.put("centerName", centerName);
        }
        List<ShelterDTO> shelterList = shelterService.selectShelterList(pageNo, pageContentEa, filterParams);
        int totalCount = shelterService.selectShelterTotalCount(filterParams);
        int totalPage = (int) Math.ceil((double) totalCount / pageContentEa);

        Map<String, Object> response = new HashMap<>();
        response.put("list", shelterList);
        response.put("totalPage", totalPage);
        return response;
    }

    // 상세 조회
    @GetMapping("/shelters/detail/{id}")
    public ShelterDTO getShelterById(@PathVariable String id) {
        return shelterService.getShelterById(id);
    }



    @PostMapping("/shelters")
    public String addShelter(@RequestBody ShelterDTO shelterDTO, @RequestHeader("userRole") String userRole) {
        if (!"ADMIN".equals(userRole)) {
            return "Unauthorized";
        }
        try {
            shelterService.insertShelter(shelterDTO);
            return "Shelter added successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error adding shelter";
        }
    }

    @DeleteMapping("/shelters/delete/{id}")
    public String deleteShelter(@PathVariable String id, @RequestHeader("userRole") String userRole) {
        if (!"ADMIN".equals(userRole)) {
            return "Unauthorized";
        }
        try {
            shelterService.deleteShelter(id);
            return "Shelter deleted successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting shelter";
        }
    }
}