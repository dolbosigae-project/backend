package com.gae.vo;

import java.util.List;

import com.gae.dto.ShelterDTO;

public class ShelterResponseVo {
	
	private List<ShelterDTO> contents;
	private PageVo pagination;
	
	    public ShelterResponseVo(List<ShelterDTO> contents, PageVo pagination) {
		super();
		this.contents = contents;
		this.pagination = pagination;
	}

		public List<ShelterDTO> getContents() {
	        return contents;
	    }

	    public PageVo getPagination() {
	        return pagination;
	    }
	}