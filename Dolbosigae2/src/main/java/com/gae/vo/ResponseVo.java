package com.gae.vo;

import java.util.List;

import com.gae.dto.PlDTO;

public class ResponseVo {
    private List<PlDTO> contents;
    private PageVo pagination;

    public ResponseVo(List<PlDTO> contents, PageVo pagination) {
        this.contents = contents;
        this.pagination = pagination;
    }

    public List<PlDTO> getContents() {
        return contents;
    }

    public PageVo getPagination() {
        return pagination;
    }
}
