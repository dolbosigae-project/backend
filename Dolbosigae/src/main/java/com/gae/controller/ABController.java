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
    public Map<String, Object> getABs(@RequestParam String shID,
                                      @RequestParam(defaultValue = "1") int pageNo,
                                      @RequestParam(defaultValue = "10") int pageContentEa) {
        List<ABDTO> abList = abService.selectABList(shID, pageNo, pageContentEa);
        int totalCount = abService.selectABTotalCount(shID);
        int totalPage = (int) Math.ceil((double) totalCount / pageContentEa);

        Map<String, Object> response = new HashMap<>();
        response.put("list", abList);
        response.put("totalPage", totalPage);

        return response;
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

