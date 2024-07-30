package com.gae.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gae.dto.ShelterDTO;
import com.gae.mapper.ShelterMapper;
import com.gae.vo.PageVo;
import com.gae.vo.ShelterResponseVo;

@Service
public class ShelterService {
    private final ShelterMapper mapper;

    public ShelterService(ShelterMapper mapper) {
        this.mapper = mapper;
    }
    
    // 쉘터 목록을 가져오는 메서드
    public ShelterResponseVo getShelterList(int page, int limit) {
        int startRow = (page - 1) * limit + 1;  // startRow 수정: 1부터 시작
        int endRow = page * limit;
        List<ShelterDTO> list = mapper.getShelterList(startRow, endRow);
        PageVo pagination = getPagination(page, limit, null, null);
        return new ShelterResponseVo(list, pagination);
    }

    // 쉘터 검색 결과를 가져오는 메서드
    public ShelterResponseVo searchShelter(String region, String centerName, int page, int limit) {
        int startRow = (page - 1) * limit + 1;  // startRow 수정: 1부터 시작
        int endRow = page * limit;
        List<ShelterDTO> list = mapper.searchShelter(region, centerName, startRow, endRow);
        PageVo pagination = getPagination(page, limit, region, centerName);
        return new ShelterResponseVo(list, pagination);
    }
    
    // 쉘터 상세 정보를 가져오는 메서드
    public ShelterDTO selectShelterInfo(String id) {
        return mapper.selectShelterInfo(id);
    }

    // 쉘터 정보를 삭제하는 메서드
    public void deleteShelter(String id) {
        mapper.deleteShelter(id);
    }

    // 페이지네이션 정보를 가져오는 메서드
    public PageVo getPagination(int page, int limit, String region, String centerName) {
        int totalCount = (region == null && centerName == null) ? mapper.getTotalCount() : mapper.getTotalCountBySearch(region, centerName);
        return new PageVo(totalCount, page, limit);
    }
    
    // 쉘터 데이터를 추가하는 메서드
    public void insertShelter(ShelterDTO shelterDTO) {
        mapper.insertShelter(shelterDTO);
    }
}