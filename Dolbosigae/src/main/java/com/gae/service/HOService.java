package com.gae.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gae.dto.HODTO;
import com.gae.mapper.HOMapper;
import com.gae.vo.HOResponseVo;
import com.gae.vo.PaggingVO;

@Service
public class HOService {
    private final HOMapper mapper;

    public HOService(HOMapper mapper) {
        this.mapper = mapper;
    }

    public HOResponseVo getHoList(int page, int limit) {
        int startRow = (page - 1) * limit + 1;
        int endRow = page * limit;
        List<HODTO> list = mapper.getHoList(startRow, endRow); // 변경된 부분
        PaggingVO pagination = getPagination(page, limit, null);
        return new HOResponseVo(list, pagination);
    }

    public HOResponseVo searchHo(String hoText, int page, int limit) { // plText를 hoText로 변경
        int startRow = (page - 1) * limit + 1;
        int endRow = page * limit;
        List<HODTO> list = mapper.searchHo(hoText, startRow, endRow); // 변경된 부분
        PaggingVO pagination = getPagination(page, limit, hoText);
        return new HOResponseVo(list, pagination);
    }

    public void deleteHo(int hoId) {
        mapper.deleteHo(hoId); // 변경된 부분
    }

    public PaggingVO getPagination(int page, int limit, String hoText) {
        int totalCount = hoText == null ? mapper.getTotalCount() : mapper.getTotalCountBySearch(hoText); // 변경된 부분
        System.out.println("Total Count: " + totalCount); // 로그 추가
        return new PaggingVO(totalCount, page, limit);
    }
}
