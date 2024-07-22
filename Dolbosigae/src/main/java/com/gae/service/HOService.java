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
        int startRow = (page - 1) * limit;
        List<HODTO> list = mapper.getHoList(startRow, limit);
        PaggingVO pagination = getPagination(page, limit, null);
        return new HOResponseVo(list, pagination);
    }

    public HOResponseVo searchHo(String hoText, int page, int limit) {
        int startRow = (page - 1) * limit;
        List<HODTO> list = mapper.searchHo(hoText, startRow, limit);
        PaggingVO pagination = getPagination(page, limit, hoText);
        return new HOResponseVo(list, pagination);
    }

    public HODTO selectHoInfo(int hoId) {
        return mapper.selectHoInfo(hoId);
    }

    public void deleteHo(int hoId) {
        mapper.deleteHo(hoId);
    }

    public PaggingVO getPagination(int page, int limit, String hoText) {
        int totalCount = hoText == null ? mapper.getTotalCount() : mapper.getTotalCountBySearch(hoText);
        return new PaggingVO(totalCount, page, limit);
    }
}








