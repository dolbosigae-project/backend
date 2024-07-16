package com.gae.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gae.dto.PlDTO;
import com.gae.mapper.PlMapper;

@Service
public class PlService {

    private final PlMapper plMapper;

    public PlService(PlMapper plMapper) {
        this.plMapper = plMapper;
    }

    public List<PlDTO> selectCity(int page, int limit) {
        int startRow = (page - 1) * limit;
        int endRow = startRow + limit;
        return plMapper.selectCity(startRow, endRow);
    }

    public int getTotalCount() {
        return plMapper.getTotalCount();
    }
    
    public List<PlDTO> searchPlaygroundDetails(String plId, String plName, String plHour, String plTel, String plAddress) {
        return plMapper.searchPlaygroundDetails(plId, plName, plHour, plTel, plAddress);
    }

    public List<PlDTO> searchCity(String plCity) {
        return plMapper.searchCity(plCity);
    }
}