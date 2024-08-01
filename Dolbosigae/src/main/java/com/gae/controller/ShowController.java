package com.gae.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.ShowDTO;
import com.gae.service.ShowService;
import com.gae.service.S3ImageService; // S3 이미지 서비스
import com.gae.vo.ShowResponseVo;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ShowController {

    private final ShowService showService;
    private final S3ImageService s3ImageService;
    private static final Logger logger = LoggerFactory.getLogger(ShowController.class);

    public ShowController(ShowService showService, S3ImageService s3ImageService) {
        this.showService = showService;
        this.s3ImageService = s3ImageService;
    }

    @GetMapping("/boards/list")
    public Map<String, Object> selectList(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "5") int limit,
        @RequestParam(required = false) String showText) {

        Map<String, Object> map = new HashMap<>();
        try {
            ShowResponseVo response;
            if (showText == null || showText.isEmpty()) { 
                response = showService.getShowList(page, limit);
            } else {
                response = showService.searchShow(showText, page, limit); 
            }

            map.put("contents", response.getContents());
            map.put("pagination", response.getPagination());
        } catch (Exception e) {
            logger.error("Error retrieving show list: " + e.getMessage(), e);
            map.put("error", "Internal Server Error");
        }
        return map;
    }

    @GetMapping("/showinfo")
    public ShowDTO selectShowInfo(@RequestParam int showNo) {
        try {
            ShowDTO result = showService.selectShowInfo(showNo);
            if (result != null) {
                return result;
            } else {
                throw new RuntimeException("Show not found");
            }
        } catch (Exception e) {
            logger.error("Error retrieving show info: " + e.getMessage(), e);
            throw new RuntimeException("Error retrieving show info");
        }
    }

    @DeleteMapping("/boards/delete/{showNo}")
    public Map<String, String> deleteShow(
            @PathVariable int showNo, 
            @RequestHeader(value = "userRole", defaultValue = "") String userRole) {

        Map<String, String> response = new HashMap<>();
        if (!"ADMIN".equals(userRole)) {
            response.put("status", "error");
            response.put("message", "Unauthorized");
            return response;
        }
        try {
            showService.deleteShow(showNo);
            response.put("status", "success");
            response.put("message", "Show deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting show: " + e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "Internal Server Error");
        }
        return response;
    }

    @Transactional
    @PostMapping("/shows")
    public Map<String, String> addShow(@RequestBody ShowDTO showDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            if (showDTO.getShowImage() != null && !showDTO.getShowImage().isEmpty()) {
                logger.info("이미지 처리 중...");

                // 이미지 데이터에서 Base64 문자열과 MIME 타입 추출
                String[] parts = showDTO.getShowImage().split(",", 2);
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid image data format");
                }

                String base64Image = parts[1]; // Base64 문자열
                String mimeType = parts[0].split(":")[1].split(";")[0]; // MIME 타입

                String extension;
                switch (mimeType) {
                    case "image/jpeg":
                        extension = "jpg";
                        break;
                    case "image/png":
                        extension = "png";
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported image type: " + mimeType);
                }

                byte[] imageBytes = Base64.getDecoder().decode(base64Image);

                logger.info("S3에 이미지 업로드 중...");
                // S3에 업로드
                String s3ImagePath = s3ImageService.uploadImageBytes(imageBytes, mimeType, extension);
                logger.info("이미지 업로드 완료. 경로: " + s3ImagePath);

                showDTO.setShowImagePath(s3ImagePath); // 이미지 경로 설정
            }
            showService.insertShow(showDTO);
            response.put("status", "success");
            response.put("message", "Show added successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid image data format: " + e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "Invalid image data format");
        } catch (Exception e) {
            logger.error("쇼 추가 중 오류 발생: " + e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "Error adding show");
        }
        return response;
    }
}
