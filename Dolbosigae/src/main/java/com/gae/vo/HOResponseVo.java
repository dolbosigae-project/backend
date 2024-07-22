package com.gae.vo;

import com.gae.dto.HODTO;

import java.util.List;

public class HOResponseVo {
	 private List<HODTO> contents;
	    private PaggingVO pagination;

	    public HOResponseVo(List<HODTO> contents, PaggingVO pagination) {
	        this.contents = contents;
	        this.pagination = pagination;
	    }

	    public List<HODTO> getContents() {
	        return contents;
	    }

	    public PaggingVO getPagination() {
	        return pagination;
	    }
	}
