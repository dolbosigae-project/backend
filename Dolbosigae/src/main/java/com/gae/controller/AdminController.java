package com.gae.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gae.dto.AdminDTO;
import com.gae.service.AdminService;
import com.gae.vo.AdminResponseVo;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AdminController {
	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	//자주 묻는 질문
	@GetMapping("/admin/default")
	public List<AdminDTO> selectAllDefault(){
		return adminService.selectAllDefault();
	}
	
	//일반 문의 게시판
	@GetMapping("/admin/contact")
	public ResponseEntity<AdminResponseVo> selectAllAdmin(@RequestParam(defaultValue = "1")int page){
		return ResponseEntity.ok(adminService.getAdminList(page));
	}
	
	//일반 문의 게시판 - 상세
    @GetMapping("/admin/contact/detail/{adminNo}")
    public ResponseEntity<AdminDTO> getAdminContactDetail(@PathVariable int adminNo) {
        AdminDTO detail = adminService.getAdminContactDetail(adminNo);
        if (detail != null) {
            return ResponseEntity.ok(detail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }	
	
}
