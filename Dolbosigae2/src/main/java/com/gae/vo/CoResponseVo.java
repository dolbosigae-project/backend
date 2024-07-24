package com.gae.vo;

import java.util.List;

import com.gae.dto.CoDTO;

public class CoResponseVo {
    private List<CoDTO> contents;
    private CoPaggingVo pagination;

    public CoResponseVo(List<CoDTO> contents, CoPaggingVo pagination) {
        this.contents = contents;
        this.pagination = pagination;
    }

    public List<CoDTO> getContents() {
        return contents;
    }

    public CoPaggingVo getPagination() {
        return pagination;
    }
}
