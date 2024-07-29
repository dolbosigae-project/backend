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

    public List<ShelterDTO> selectShelterList(int pageNo, int pageContentEa, Map<String, Object> filterParams) {
        Map<String, Object> map = new HashMap<>();
        map.put("startRow", (pageNo - 1) * pageContentEa);
        map.put("pageContentEa", pageContentEa);
        map.putAll(filterParams);
        return mapper.selectShelterList(map);
    }

    public int selectShelterTotalCount(Map<String, Object> filterParams) {
        return mapper.selectShelterTotalCount(filterParams);
    }

    public void insertShelter(ShelterDTO shelterDTO) {
        mapper.insertShelter(shelterDTO);
    }

    public void deleteShelter(String id) {
        mapper.deleteShelter(id);
    }
    
    public ShelterDTO getShelterById(String id) {
        return mapper.selectShelterById(id);
    }

    public List<ShelterDTO> getAllShelters() {
        return mapper.selectAllShelters();
    }
}