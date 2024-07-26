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

    @GetMapping("/shelters")
    public Map<String, Object> getShelters(@RequestParam(defaultValue = "1") int pageNo,
                                           @RequestParam(defaultValue = "10") int pageContentEa) {
        List<ShelterDTO> shelterList = shelterService.selectShelterList(pageNo, pageContentEa);
        int totalCount = shelterService.selectShelterTotalCount();
        int totalPage = (int) Math.ceil((double) totalCount / pageContentEa);

        Map<String, Object> response = new HashMap<>();
        response.put("list", shelterList);
        response.put("totalPage", totalPage);

        return response;
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

    @DeleteMapping("/shelters/{id}")
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
