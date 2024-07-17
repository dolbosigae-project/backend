package com.gae.vo;

import java.util.List;

import com.gae.dto.PlSearchViewDTO;

public class PlResponseVo {
    private List<PlSearchViewDTO> contents;
    private PlPageVo pagination;

    public PlResponseVo(List<PlSearchViewDTO> contents, PlPageVo pagination) {
        this.contents = contents;
        this.pagination = pagination;
    }

    public List<PlSearchViewDTO> getContents() {
        return contents;
    }

    public PlPageVo getPagination() {
        return pagination;
    }
}
