package com.gae.vo;

import java.util.List;

import com.gae.dto.AdminDTO;
import com.gae.dto.BoardMemberDTO;

public class AdminResponseVo {
    private List<AdminDTO> admin;
    private AdminPaggingVo pagination;

    public AdminResponseVo(List<AdminDTO> admin, AdminPaggingVo pagination) {
		this.admin = admin;
		this.pagination = pagination;
	}

	public List<AdminDTO> getAdmin() {
		return admin;
	}

	public void setAdmin(List<AdminDTO> admin) {
		this.admin = admin;
	}

	public AdminPaggingVo getPagination() {
		return pagination;
	}
	
	public void setPagination(AdminPaggingVo pagination) {
		this.pagination = pagination;
	}


	

}
