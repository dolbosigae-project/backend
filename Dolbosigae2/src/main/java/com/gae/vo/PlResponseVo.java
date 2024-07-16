package com.gae.vo;

import java.util.List;

import com.gae.dto.PlDTO;

public class PlResponseVo {
    private List<PlDTO> contents;
    private PlPageVo pagination;

    public PlResponseVo(List<PlDTO> contents, PlPageVo pagination) {
        this.contents = contents;
        this.pagination = pagination;
    }

    public List<PlDTO> getContents() {
        return contents;
    }

    public PlPageVo getPagination() {
        return pagination;
    }
}
