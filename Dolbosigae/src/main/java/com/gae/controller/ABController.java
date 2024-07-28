package com.gae.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.gae.dto.ABDTO;
import com.gae.service.ABService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://nam3324.synology.me:32800"})
public class ABController {

    private final ABService abService;

    public ABController(ABService abService) {
        this.abService = abService;
    }

    @GetMapping("/ab")
    public Map<String, Object> getABs(@RequestParam(defaultValue = "1") int pageNo,
                                      @RequestParam(defaultValue = "6") int pageContentEa) {
        List<ABDTO> abList = abService.selectABList(pageNo, pageContentEa, null);
        int totalCount = abService.selectABTotalCount(null);
        int totalPage = (int) Math.ceil((double) totalCount / pageContentEa);

        Map<String, Object> response = new HashMap<>();
        response.put("list", abList);
        response.put("totalPage", totalPage);

        return response;
    }

    @GetMapping("/ab/search")
    public Map<String, Object> searchABs(@RequestParam String shID,
                                         @RequestParam(defaultValue = "1") int pageNo,
                                         @RequestParam(defaultValue = "6") int pageContentEa,
                                         @RequestParam(required = false) String region,
                                         @RequestParam(required = false) String centerName,
                                         @RequestParam(required = false) String startDate,
                                         @RequestParam(required = false) String endDate,
                                         @RequestParam(required = false) String breed) {

        Map<String, Object> filterParams = new HashMap<>();
        filterParams.put("shID", shID);
        if (region != null && !region.isEmpty()) {
            filterParams.put("region", region);
        }
        if (centerName != null && !centerName.isEmpty()) {
            filterParams.put("centerName", centerName);
        }
        if (startDate != null && !startDate.isEmpty()) {
            filterParams.put("startDate", startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            filterParams.put("endDate", endDate);
        }
        if (breed != null && !breed.isEmpty()) {
            filterParams.put("breed", breed);
        }

        List<ABDTO> abList = abService.selectABList(pageNo, pageContentEa, filterParams);
        int totalCount = abService.selectABTotalCount(filterParams);
        int totalPage = (int) Math.ceil((double) totalCount / pageContentEa);

        Map<String, Object> response = new HashMap<>();
        response.put("list", abList);
        response.put("totalPage", totalPage);

        return response;
    }

    @GetMapping("/ab/detail/{id}")
    public ABDTO getABDetail(@PathVariable String id) {
        return abService.selectABDetail(id);
    }

    @PostMapping("/ab")
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

    @DeleteMapping("/ab/{id}")
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