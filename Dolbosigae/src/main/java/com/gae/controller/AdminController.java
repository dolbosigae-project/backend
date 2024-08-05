package com.gae.controller;

import java.util.List;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.AdminDTO;
import com.gae.service.AdminService;
import com.gae.vo.AdminResponseVo;

@CrossOrigin(origins = "https://www.dolbosigae.site")
@RestController
public class AdminController {
	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	// 자주 묻는 질문
	@GetMapping("/admin/default")
	public List<AdminDTO> selectAllDefault(){
		return adminService.selectAllDefault();
	}
	
	// 일반 문의 게시판
	@GetMapping("/admin/contact")
	public ResponseEntity<AdminResponseVo> selectAllAdmin(@RequestParam(defaultValue = "1")int page){
		return ResponseEntity.ok(adminService.getAdminList(page));
	}
	
	// 일반 문의 게시판 : 상세
    @GetMapping("/admin/contact/detail/{adminNo}")
    public ResponseEntity<AdminDTO> getAdminContactDetail(@PathVariable int adminNo) {
        AdminDTO detail = adminService.getAdminContactDetail(adminNo);
        if (detail != null) {
            return ResponseEntity.ok(detail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }	
    
    // 일반 문의 게시판 : 글쓰기
    @PostMapping("/admin/write")
    public ResponseEntity<String> writeAdmin(@RequestBody AdminDTO admin){
    	System.out.println(admin);
    	adminService.writeAdmin(admin);
    	return ResponseEntity.ok("회원가입 성공");
    }
    
    // 일반 문의 게시판 : 댓글쓰기
    @PostMapping("/admin/comment/write")
    public ResponseEntity<String> writeAdminComment(@RequestBody AdminDTO admin){
    	System.out.println(admin);
    	adminService.writeAdminComment(admin);
    	return ResponseEntity.ok("댓글 등록 성공");
    }
    
    // 문의글 삭제
    @DeleteMapping("/admin/delete/{adminNo}/{adminCommentCount}")
    public ResponseEntity<String> adminDelete(@PathVariable int adminNo, @PathVariable int adminCommentCount){
    	// 댓글이 있을 경우 댓글 먼저 삭제    	
    	if(adminCommentCount > 0) {
    		int commentResult = adminService.deleteAllComment(adminNo);
    	}
    	int parentResult = adminService.deleteAdmin(adminNo);
    	if(parentResult != 0) {
    		return ResponseEntity.ok("문의글 삭제 성공");
    	} else {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문의글 삭제 실패");
    	}
    }
    
    // 댓글 삭제
    @DeleteMapping("/admin/delete/{adminCommentNo}")
    public ResponseEntity<String> adminCommentDelete(@PathVariable int adminCommentNo){
    	int result = adminService.adminCommentDelete(adminCommentNo);
    	if(result != 0) {
    		return ResponseEntity.ok("댓글 삭제 성공");
    	}else {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제 실패");
    	}
    }
    
    // 카테고리별로 문의글 서치 
    @GetMapping("/admin/search")
    public ResponseEntity<?> adminSearch(@RequestParam String category, @RequestParam String term){
    	List<Admin> searchResults = adminService.searchAdmin(category, term);
    	return ResponseEntity.ok(searchResults);
    }
    
    //미답변 글 확인
    @GetMapping("/admin/no-answer")
    public ResponseEntity<List<AdminDTO>> noAnswerOnly() {
        return ResponseEntity.ok(adminService.noAnswerOnly());
    }
    
    
	
}

























