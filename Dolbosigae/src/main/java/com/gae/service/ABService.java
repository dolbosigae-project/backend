package com.gae.service;

import org.springframework.stereotype.Service;

import com.gae.dto.ABDTO;
import com.gae.mapper.ABMapper;
import com.gae.vo.PageVo;
import com.gae.vo.ABResponseVo;

import java.util.List;

@Service
public class ABService {
    private final ABMapper mapper;

    public ABService(ABMapper mapper) {
        this.mapper = mapper;
    }

    // AB 목록을 가져오는 메서드
    public ABResponseVo getABList(int page, int limit) {
        int startRow = (page - 1) * limit + 1;
        int endRow = page * limit;
        List<ABDTO> list = mapper.getABList(startRow, endRow);
        PageVo pagination = getPagination(page, limit); // 기본 페이지네이션
        return new ABResponseVo(list, pagination);
    }

    // AB 검색 결과를 가져오는 메서드
    public ABResponseVo searchAB(String region, String breed, int page, int limit) {
        int startRow = (page - 1) * limit + 1;
        int endRow = page * limit;
        List<ABDTO> list = mapper.searchAB(region, breed, startRow, endRow);
        PageVo pagination = getPagination(page, limit, region, breed);
        return new ABResponseVo(list, pagination);
    }

    // AB 상세 정보를 가져오는 메서드
    public ABDTO selectABDetail(String id) {
        return mapper.selectABDetail(id);
    }

    // 페이지네이션 정보를 가져오는 메서드
    public PageVo getPagination(int page, int limit) {
        int totalCount = mapper.getTotalCount();
        return new PageVo(totalCount, page, limit);
    }

    // 검색 조건을 포함한 페이지네이션 정보를 가져오는 메서드
    public PageVo getPagination(int page, int limit, String region, String breed) {
        int totalCount = mapper.getTotalCountBySearch(region, breed);
        return new PageVo(totalCount, page, limit);
    }

    // AB 데이터를 추가하는 메서드
    public void insertAB(ABDTO abDTO) {
        mapper.insertAB(abDTO);
    }

    // AB 데이터를 삭제하는 메서드
    public void deleteAB(String id) {
        mapper.deleteAB(id);
    }
}
