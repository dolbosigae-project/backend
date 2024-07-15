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
        map.put("pageNo", (pageNo - 1) * pageContentEa); 
        map.put("pageContentCount", pageContentEa);
        return mapper.selectShelterList(map);
    }

    public int selectShelterTotalCount() {
        // 전체 게시글 수를 반환하는 메서드
        return mapper.selectShelterTotalCount();
    }
}
