package com.gae.service;

import org.springframework.stereotype.Service;
import com.gae.mapper.PlMapper;
import com.gae.dto.PlDTO;
import com.gae.vo.PlPaggingVo;
import com.gae.vo.PlResponseVo;

import java.util.List;

@Service
public class PlService {

    private final PlMapper plMapper;

    public PlService(PlMapper plMapper) {
        this.plMapper = plMapper;
    }

    public PlResponseVo getCityList(int page, int limit) {
        int startRow = (page - 1) * limit + 1;
        int endRow = page * limit;
        List<PlDTO> list = plMapper.getCityList(startRow, endRow);
        PlPaggingVo pagination = getPagination(page, limit, null);
        return new PlResponseVo(list, pagination);
    }

    public PlResponseVo searchCity(String plText, int page, int limit) {
        int startRow = (page - 1) * limit + 1;
        int endRow = page * limit;
        List<PlDTO> list = plMapper.searchCity(plText, startRow, endRow);
        PlPaggingVo pagination = getPagination(page, limit, plText);
        return new PlResponseVo(list, pagination);
    }

    public PlDTO selectCityInfo(int plId) {
        return plMapper.selectCityInfo(plId);
    }

    public void deleteCity(int plId) {
        plMapper.deleteCity(plId);
    }

    public PlPaggingVo getPagination(int page, int limit, String plText) {
        int totalCount = plText == null ? plMapper.getTotalCount() : plMapper.getTotalCountBySearch(plText);
        System.out.println("Total Count: " + totalCount); // 로그 추가
        return new PlPaggingVo(totalCount, page, limit);
    }
}