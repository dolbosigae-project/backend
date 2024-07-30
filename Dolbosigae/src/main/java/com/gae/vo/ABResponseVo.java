package com.gae.vo;

import java.util.List;
import com.gae.dto.ABDTO;

public class ABResponseVo {
    private List<ABDTO> contents;
    private PageVo pagination;

    public ABResponseVo(List<ABDTO> contents, PageVo pagination) {
        this.contents = contents;
        this.pagination = pagination;
    }

    public List<ABDTO> getContents() {
        return contents;
    }

    public PageVo getPagination() {
        return pagination;
    }
}
