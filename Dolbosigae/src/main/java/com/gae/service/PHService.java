package com.gae.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gae.dto.PHDTO;
import com.gae.mapper.PHMapper;

@Service
public class PHService {
    private PHMapper mapper;
    
    public PHService(PHMapper mapper) {
        this.mapper= mapper;
    }
    
    public List<PHDTO> selectPhNewList(int pageNo, int pageContentEa) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageNo", pageNo);
        map.put("pageContentCount", pageContentEa);
        return mapper.selectPhNewList(map);
    }
    
    public int selectPhTotalCount() {
        return mapper.selectPhTotalCount();
    }

    public PHDTO selectPh(int pno) {
        return mapper.selectPh(pno);
    }
}