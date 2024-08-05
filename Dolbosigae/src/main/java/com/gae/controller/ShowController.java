package com.gae.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
        @RequestParam(defaultValue = "10") int limit,
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

    @PostMapping("/upload")
    public Map<String, String> uploadImg(@RequestBody Map<String, String> payload){
        Map<String, String> response = new HashMap<>();
        try {
            String base64Image = payload.get("image"); // 프론트엔드에서 보낸 이미지 데이터 (Base64 인코딩)
            if (base64Image == null || base64Image.isEmpty()) {
                throw new IllegalArgumentException("No image data found");
            }

            String[] parts = base64Image.split(",", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid image data format");
            }

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

            byte[] imageBytes = Base64.getDecoder().decode(parts[1]);

            logger.info("S3에 이미지 업로드 중...");
            // S3에 업로드
            String s3ImagePath = s3ImageService.uploadImageBytes(imageBytes, mimeType, extension);
            logger.info("이미지 업로드 완료. 경로: " + s3ImagePath);

            response.put("status", "success");
            response.put("fileUrl", s3ImagePath); // 업로드된 이미지 경로 반환
        } catch (IllegalArgumentException e) {
            logger.error("Invalid image data format: " + e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "Invalid image data format");
        } catch (Exception e) {
            logger.error("이미지 업로드 중 오류 발생: " + e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "Error uploading image");
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
                // showDTO.getShowImage()에 S3 경로가 설정되었다고 가정하고 저장

                showDTO.setShowImagePath(showDTO.getShowImage()); // 이미지 경로 설정
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
    
    @Transactional
    @PostMapping("/shows/{id}")
    public ResponseEntity<String> editShow(@RequestBody ShowDTO showDTO) {
    	   try {
               System.out.println("Received Member Data: " + showDTO); // 데이터 출력
               int result = showService.editShow(showDTO);
               System.out.println("Update Result: " + result);

               return ResponseEntity.ok("게시글 정보가 업데이트되었습니다.");
           } catch (Exception e) {
               e.printStackTrace();
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 정보 업데이트 중 오류가 발생했습니다.");
           }
       }
    
    
    
    
    
}
