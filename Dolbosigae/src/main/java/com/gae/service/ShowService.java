package com.gae.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gae.dto.ShowDTO;
import com.gae.mapper.ShowMapper;
import com.gae.vo.PaggingVO;
import com.gae.vo.ShowResponseVo;

@Service
public class ShowService {
    private final ShowMapper mapper;

    public ShowService(ShowMapper mapper) {
        this.mapper = mapper;
    }

    // Show 목록을 가져오는 메서드
    public ShowResponseVo getShowList(int page, int limit) {
        int startRow = (page - 1) * limit + 1;  // startRow 수정: 1부터 시작
        int endRow = page * limit;
        List<ShowDTO> list = mapper.getShowList(startRow, endRow);
        PaggingVO pagination = getPagination(page, limit, null);
        return new ShowResponseVo(list, pagination);
    }

    // Show 검색 결과를 가져오는 메서드
    public ShowResponseVo searchShow(String showText, int page, int limit) {
        int startRow = (page - 1) * limit + 1;  // startRow 수정: 1부터 시작
        int endRow = page * limit;
        List<ShowDTO> list = mapper.searchShow(showText, startRow, endRow);
        PaggingVO pagination = getPagination(page, limit, showText);
        return new ShowResponseVo(list, pagination);
    }

    // Show 상세 정보를 가져오는 메서드
    public ShowDTO selectShowInfo(int showNo) {
        return mapper.selectShowInfo(showNo);
    }

    // Show 정보를 삭제하는 메서드
    public void deleteShow(int showNo) {
        mapper.deleteShow(showNo);
    }

    // 페이지네이션 정보를 가져오는 메서드
    public PaggingVO getPagination(int page, int limit, String showText) {
        int totalCount = showText == null ? mapper.getTotalCount() : mapper.getTotalCountBySearch(showText);
        return new PaggingVO(totalCount, page, limit);
    }

    public void insertShow(ShowDTO showDTO) {
        // SHOW_NO는 자동으로 생성되므로, DTO에서 설정하지 않도록 합니다.
        mapper.insertShow(showDTO);
    } 
    
    
    
    

    public void writeBoard(ShowDTO showDTO) {
       
        mapper.writeBoard(showDTO);
    }
}
