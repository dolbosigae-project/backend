package com.gae.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gae.dto.ShelterDTO;
import com.gae.mapper.ShelterMapper;

@Service
public class ShelterService {

    private final ShelterMapper mapper;

    public ShelterService(ShelterMapper mapper) {
        this.mapper = mapper;
    }

    public List<ShelterDTO> selectShelterList(int pageNo, int pageContentEa) {
        Map<String, Object> map = new HashMap<>();
        int startRow = (pageNo - 1) * pageContentEa + 1;
        int endRow = pageNo * pageContentEa;
        map.put("startRow", startRow);
        map.put("endRow", endRow);
        System.out.println("Fetching shelter list with startRow: " + startRow + " and endRow: " + endRow);
        List<ShelterDTO> result = mapper.selectShelterList(map);
        System.out.println("Fetched " + result.size() + " shelters");
        return result;
    }

    public int selectShelterTotalCount() {
        int totalCount = mapper.selectShelterTotalCount();
        System.out.println("Total count of shelters: " + totalCount);
        return totalCount;
    }
}