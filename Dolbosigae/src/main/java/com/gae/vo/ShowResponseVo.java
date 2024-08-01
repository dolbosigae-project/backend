package com.gae.vo;

import java.util.List;

import com.gae.dto.ShowDTO;

public class ShowResponseVo {
	  private List<ShowDTO> contents;
	    private PaggingVO pagination;

	    public ShowResponseVo(List<ShowDTO> contents, PaggingVO pagination) {
	        this.contents = contents;
	        this.pagination = pagination;
	    }

	    public List<ShowDTO> getContents() {
	        return contents;
	    }

	    public PaggingVO getPagination() {
	        return pagination;
	    }
	}