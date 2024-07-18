package com.gae.controller;

import com.gae.vo.HOResponseVo;
import com.gae.vo.PaggingVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.HODTO;
import com.gae.service.HOService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class HOController {

    private final HOService hoService;

    public HOController(HOService hoService) {
        this.hoService = hoService;
    }

    @GetMapping("/hospitals")
    public ResponseEntity<HOResponseVo> getHospitals(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        List<HODTO> hospitals = hoService.searchHo(page, limit);
        int totalCount = hoService.selectTotalHoCount();
        PaggingVO paggingVO = new PaggingVO(totalCount, page, limit);

        HOResponseVo responseVo = new HOResponseVo(hospitals, paggingVO);
        return ResponseEntity.ok(responseVo);
    }
}
