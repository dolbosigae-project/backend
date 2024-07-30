package com.gae.vo;

import com.gae.dto.PHDTO; // HODTO를 PHDTO로 변경

import java.util.List;

public class PHResponseVo { // HOResponseVo를 PHResponseVo로 변경
    private List<PHDTO> contents; // HODTO를 PHDTO로 변경
    private PaggingVO pagination;

    public PHResponseVo(List<PHDTO> contents, PaggingVO pagination) { // HODTO를 PHDTO로 변경
        this.contents = contents;
        this.pagination = pagination;
    }

    public List<PHDTO> getContents() { // HODTO를 PHDTO로 변경
        return contents;
    }

    public PaggingVO getPagination() {
        return pagination;
    }
}
