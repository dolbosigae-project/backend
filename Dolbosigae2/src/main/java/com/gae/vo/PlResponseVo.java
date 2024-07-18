package com.gae.vo;

import java.util.List;

import com.gae.dto.PlSearchViewDTO;

public class PlResponseVo {
    private List<PlSearchViewDTO> contents;
    private PlPaggingVo pagination;

    public PlResponseVo(List<PlSearchViewDTO> contents, PlPaggingVo pagination) {
        this.contents = contents;
        this.pagination = pagination;
    }

    public List<PlSearchViewDTO> getContents() {
        return contents;
    }

    public PlPaggingVo getPagination() {
        return pagination;
    }
}
