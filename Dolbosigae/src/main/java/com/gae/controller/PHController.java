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

import com.gae.dto.PHDTO; // HODTO를 PHDTO로 변경
import com.gae.service.PHService;
import com.gae.vo.HOResponseVo; // VO 클래스도 PHResponseVo로 변경할 필요 있음
import com.gae.vo.PHResponseVo;

@RestController
@CrossOrigin(origins = "https://dolbosigae.site", allowedHeaders = "*")
public class PHController { // 클래스 이름도 HOController에서 PHController로 변경

    private final PHService phService; // 서비스 이름도 HOService에서 PHService로 변경

    public PHController(PHService phService) { // 생성자에서 HOService도 PHService로 변경
        this.phService = phService;
    }

    @GetMapping("/pharmacies/list") // 엔드포인트 변경
    public Map<String, Object> selectPhList(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "5") int limit,
        @RequestParam(required = false) String phText) { // 요청 파라미터도 phText로 변경

        Map<String, Object> map = new HashMap<>();
        try {
            PHResponseVo response; // VO 클래스도 PHResponseVo로 변경
            if (phText == null || phText.isEmpty()) { 
                response = phService.getPhList(page, limit); // 메서드도 PHService의 대응 메서드로 변경
            } else {
                response = phService.searchPh(phText, page, limit); // 메서드도 PHService의 대응 메서드로 변경
            }

            map.put("contents", response.getContents());
            map.put("pagination", response.getPagination());
        } catch (Exception e) {
            e.printStackTrace(); // 전체 스택 트레이스를 로그에 출력하여 오류 원인을 명확히 파악합니다
            map.put("error", "Internal Server Error");
        }
        return map;
    }
    
    @GetMapping("/phinfo") // 엔드포인트 변경
    public PHDTO selectPhInfo(@RequestParam int phId) { // 파라미터와 반환 타입도 변경
        System.out.println(phId);
        PHDTO result = phService.selectPhInfo(phId); // 메서드도 PHService의 대응 메서드로 변경
        System.out.println(result);
        return result;
    }
    
    @DeleteMapping("/pharmacies/delete/{phId}") // 엔드포인트 변경
    public Map<String, String> deletePharmacies(
    		@PathVariable int phId,
    		 @RequestHeader(value = "userRole", defaultValue = "") String userRole) {

    		{ // 파라미터도 변경
        Map<String, String> response = new HashMap<>();
        if (!"ADMIN".equals(userRole)) {
            response.put("status", "error");
            response.put("message", "Unauthorized");
            return response;
        }
        try {
            phService.deletePh(phId);
            response.put("status", "success");
            response.put("message", "Pharmacy deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Internal Server Error");
        }
        return response;
}
    }
    
    @PostMapping("/pharmacies")
    public String addPharmacies(@RequestBody PHDTO phDTO) {
        try {
            phService.insertPh(phDTO);
            return "pharmacy added successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error adding pharmacy";
        }

    }
}