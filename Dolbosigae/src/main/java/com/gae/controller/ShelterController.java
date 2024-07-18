package com.gae.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gae.dto.ShelterDTO;
import com.gae.service.ShelterService;
import com.gae.vo.PageVo;

@CrossOrigin(origins = {"http://localhost:3000", "http://nam3324.synology.me:32800"})
@RestController
public class ShelterController {

    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }
    
    @GetMapping("/shelter")
    public Map<String, Object> shelter(@RequestParam(defaultValue = "1") int pageNo,
                                       @RequestParam(defaultValue = "10") int pageContentEa) {
        List<ShelterDTO> shelterList = shelterService.selectShelterList(pageNo, pageContentEa);
        int totalCount = shelterService.selectShelterTotalCount();
        int totalPage = (int) Math.ceil((double) totalCount / pageContentEa);

        Map<String, Object> response = new HashMap<>();
        response.put("list", shelterList);
        response.put("totalPage", totalPage);

        return response;
    }
}
