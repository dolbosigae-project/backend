package com.gae.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gae.dto.ABDTO;
import com.gae.mapper.ABMapper;

@Service
public class ABService {
    private final ABMapper mapper;

    public ABService(ABMapper mapper) {
        this.mapper = mapper;
    }

    public List<ABDTO> selectABList(int pageNo, int pageContentEa, Map<String, Object> filterParams) {
        Map<String, Object> map = new HashMap<>();
        map.put("startRow", (pageNo - 1) * pageContentEa);
        map.put("pageContentEa", pageContentEa);
        if (filterParams != null && !filterParams.isEmpty()) {
            map.putAll(filterParams);
            return mapper.selectFilteredABList(map); // 필터링된 조회
        } else {
            return mapper.selectABList(map); // 전체 조회
        }
    }

    public int selectABTotalCount(Map<String, Object> filterParams) {
        return mapper.selectABTotalCount(filterParams);
    }

    public void insertAB(ABDTO abDTO) {
        mapper.insertAB(abDTO);
    }

    public void deleteAB(String id) {
        mapper.deleteAB(id);
    }

    public ABDTO selectABDetail(String id) {
        return mapper.selectABDetail(id);
    }
}