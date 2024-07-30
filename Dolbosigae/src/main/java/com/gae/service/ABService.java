package com.gae.service;

import org.springframework.stereotype.Service;

import com.gae.dto.ABDTO;
import com.gae.mapper.ABMapper;
import com.gae.vo.ABResponseVo;
import com.gae.vo.PageVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ABService {
    private final ABMapper mapper;

    public ABService(ABMapper mapper) {
        this.mapper = mapper;
    }

    public ABResponseVo getABList(int page, int limit) {
        int startRow = (page - 1) * limit + 1;
        int endRow = page * limit;
        List<ABDTO> list = mapper.selectABList(startRow, endRow);
        PageVo pagination = getPagination(page, limit, null);
        return new ABResponseVo(list, pagination);
    }

    public ABResponseVo searchAB(String shID, String region, String centerName, String startDate, String endDate, String breed, int page, int limit) {
        int startRow = (page - 1) * limit + 1;
        int endRow = page * limit;
        Map<String, Object> filterParams = new HashMap<>();
        filterParams.put("shID", shID);
        filterParams.put("region", region);
        filterParams.put("centerName", centerName);
        filterParams.put("startDate", startDate);
        filterParams.put("endDate", endDate);
        filterParams.put("breed", breed);
        List<ABDTO> list = mapper.selectFilteredABList(startRow, endRow, filterParams);
        PageVo pagination = getPagination(page, limit, filterParams);
        return new ABResponseVo(list, pagination);
    }

    public ABDTO selectABDetail(String id) {
        return mapper.selectABDetail(id);
    }

    public void insertAB(ABDTO abDTO) {
        mapper.insertAB(abDTO);
    }

    public void deleteAB(String id) {
        mapper.deleteAB(id);
    }

    public PageVo getPagination(int page, int limit, Map<String, Object> filterParams) {
        int totalCount = (filterParams == null || filterParams.isEmpty()) ? mapper.selectABTotalCount(null) : mapper.selectABTotalCount(filterParams);
        return new PageVo(totalCount, page, limit);
    }
}
