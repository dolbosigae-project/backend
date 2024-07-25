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

    public List<ABDTO> selectABList(String shID, int pageNo, int pageContentEa) {
        Map<String, Object> map = new HashMap<>();
        map.put("shID", shID);
        map.put("startRow", (pageNo - 1) * pageContentEa);
        map.put("pageContentEa", pageContentEa);
        return mapper.selectABList(map);
    }

    public int selectABTotalCount(String shID) {
        return mapper.selectABTotalCount(shID);
    }

    public void insertAB(ABDTO abDTO) {
        mapper.insertAB(abDTO);
    }

    public void deleteAB(String id) {
        mapper.deleteAB(id);
    }
}
