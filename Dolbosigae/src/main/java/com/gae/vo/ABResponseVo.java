package com.gae.vo;

import java.util.List;

import com.gae.dto.ABDTO;

public class ABResponseVo {
	private List<ABDTO> ab;
	private ABPaggingVo pagination;

	public ABResponseVo(List<ABDTO> ab, ABPaggingVo pagination) {
		this.ab = ab;
		this.pagination = pagination;
	}

	public List<ABDTO> getAb() {
		return ab;
	}

	public void setAb(List<ABDTO> ab) {
		this.ab = ab;
	}

	public ABPaggingVo getPagination() {
		return pagination;
	}

	public void setPagination(ABPaggingVo pagination) {
		this.pagination = pagination;
	}

	
	
}