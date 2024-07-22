package com.gae.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gae.dto.AdminDTO;
import com.gae.mapper.AdminMapper;
import com.gae.vo.AdminPaggingVo;
import com.gae.vo.AdminResponseVo;

@Service
public class AdminService {
	@Autowired
	private AdminMapper adminMapper;

	public AdminService(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}

	//자주 묻는 질문	
	public List<AdminDTO> selectAllDefault() {
		return adminMapper.selectAllDefault();
	}

	//일반 문의 게시판
	public AdminResponseVo getAdminList(int page) {
        int pageOfContentCount = 10; // 페이지당 멤버 수
        int totalCount = adminMapper.getTotalCount(); // 전체 멤버 수 가져오기
        AdminPaggingVo paggingVo = new AdminPaggingVo(totalCount, page, pageOfContentCount);

        int startRow = (page - 1) * pageOfContentCount;
        int endRow = startRow + pageOfContentCount;
        List<AdminDTO> admins = adminMapper.getAdminList(startRow, endRow);
        return new AdminResponseVo(admins, paggingVo);
	}

	//일반 문의 게시판 - 상세
	public AdminDTO getAdminContactDetail(int adminNo) {
        return adminMapper.getAdminContactDetail(adminNo);
    }

	
	

}
