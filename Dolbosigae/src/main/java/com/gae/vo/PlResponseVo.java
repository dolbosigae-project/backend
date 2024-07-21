package com.gae.vo;

import java.util.List;

import com.gae.dto.PlDTO;

public class PlResponseVo {
    private List<PlDTO> contents;
    private PlPaggingVo pagination;

    public PlResponseVo(List<PlDTO> contents, PlPaggingVo pagination) {
        this.contents = contents;
        this.pagination = pagination;
    }

    public List<PlDTO> getContents() {
        return contents;
    }

    public PlPaggingVo getPagination() {
        return pagination;
    }
}