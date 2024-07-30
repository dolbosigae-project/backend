package com.gae.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.gae.dto.ABDTO;
import com.gae.service.ABService;
import com.gae.vo.ABResponseVo;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://nam3324.synology.me:32800"})
public class ABController {

    private final ABService abService;

    public ABController(ABService abService) {
        this.abService = abService;
    }

    @GetMapping("/abs/list")
    public Map<String, Object> selectABList(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(required = false) String shID,
        @RequestParam(required = false) String region,
        @RequestParam(required = false) String centerName,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate,
        @RequestParam(required = false) String breed) {

        Map<String, Object> map = new HashMap<>();
        try {
            ABResponseVo response;
            if ((shID == null || shID.isEmpty()) && (region == null || region.isEmpty()) && 
                (centerName == null || centerName.isEmpty()) && (startDate == null || startDate.isEmpty()) && 
                (endDate == null || endDate.isEmpty()) && (breed == null || breed.isEmpty())) { 
                response = abService.getABList(page, limit);
            } else {
                response = abService.searchAB(shID, region, centerName, startDate, endDate, breed, page, limit); 
            }

            map.put("contents", response.getContents());
            map.put("pagination", response.getPagination());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "Internal Server Error");
        }
        return map;
    }
    
    @GetMapping("/abdetail/{id}")
    public ABDTO getABDetail(@PathVariable String id) {
        // `id`가 `abID`를 의미
        ABDTO abDetail = abService.selectABDetail(id);
        if (abDetail == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "AB detail not found");
        }
        return abDetail;
    }

    
    @PostMapping("/abs")
    public String addAB(@RequestBody ABDTO abDTO, @RequestHeader("userRole") String userRole) {
        if (!"ADMIN".equals(userRole)) {
            return "Unauthorized";
        }
        try {
            abService.insertAB(abDTO);
            return "AB added successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error adding AB";
        }
    }
    
    @DeleteMapping("/abs/{id}")
    public String deleteAB(@PathVariable String id, @RequestHeader("userRole") String userRole) {
        if (!"ADMIN".equals(userRole)) {
            return "Unauthorized";
        }
        try {
            abService.deleteAB(id);
            return "AB deleted successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting AB";
        }
    }
}
