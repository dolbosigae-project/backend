package com.gae.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gae.dto.HODTO;
import com.gae.mapper.HOMapper;
import com.gae.vo.HOResponseVo;
import com.gae.vo.PaggingVO;

@Service
public class HOService {
    private final HOMapper mapper;

    public HOService(HOMapper mapper) {
        this.mapper = mapper;
    }
    
    
    // 병원 목록을 가져오는 메서드
    public HOResponseVo getHoList(int page, int limit) {
        int startRow = (page - 1) * limit + 1;  // startRow 수정: 1부터 시작
        int endRow = page * limit;
        List<HODTO> list = mapper.getHoList(startRow, endRow);
        PaggingVO pagination = getPagination(page, limit, null);
        return new HOResponseVo(list, pagination);
    }

    // 병원 검색 결과를 가져오는 메서드
    public HOResponseVo searchHo(String hoText, int page, int limit) {
        int startRow = (page - 1) * limit + 1;  // startRow 수정: 1부터 시작
        int endRow = page * limit;
        List<HODTO> list = mapper.searchHo(hoText, startRow, endRow);
        PaggingVO pagination = getPagination(page, limit, hoText);
        return new HOResponseVo(list, pagination);
    }
    
    
    // 병원 상세 정보를 가져오는 메서드
    public HODTO selectHoInfo(int hoId) {
        return mapper.selectHoInfo(hoId);
    }
    // 병원 정보를 삭제하는 메서드
    public void deleteHo(int hoId) {
        mapper.deleteHo(hoId);
    }
 // 페이지네이션 정보를 가져오는 메서드
    public PaggingVO getPagination(int page, int limit, String hoText) {
        int totalCount = hoText == null ? mapper.getTotalCount() : mapper.getTotalCountBySearch(hoText);
        return new PaggingVO(totalCount, page, limit);
    }
}
