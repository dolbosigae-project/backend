package com.gae.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gae.dto.PHDTO; // HODTO를 PHDTO로 변경
import com.gae.mapper.PHMapper; // HOMapper를 PHMapper로 변경
import com.gae.vo.PHResponseVo; // HOResponseVo를 PHResponseVo로 변경
import com.gae.vo.PaggingVO;

@Service
public class PHService { // HOService를 PHService로 변경
    private final PHMapper mapper; // HOMapper를 PHMapper로 변경

    public PHService(PHMapper mapper) { // 생성자에서 HOMapper를 PHMapper로 변경
        this.mapper = mapper;
    }
    
    // 약국 목록을 가져오는 메서드
    public PHResponseVo getPhList(int page, int limit) { // HOResponseVo를 PHResponseVo로 변경
        int startRow = (page - 1) * limit + 1;  // startRow 수정: 1부터 시작
        int endRow = page * limit;
        List<PHDTO> list = mapper.getPhList(startRow, endRow); // HODTO를 PHDTO로 변경
        PaggingVO pagination = getPagination(page, limit, null);
        return new PHResponseVo(list, pagination); // HOResponseVo를 PHResponseVo로 변경
    }

    // 약국 검색 결과를 가져오는 메서드
    public PHResponseVo searchPh(String phText, int page, int limit) { // HOResponseVo를 PHResponseVo로 변경, hoText를 phText로 변경
        int startRow = (page - 1) * limit + 1;  // startRow 수정: 1부터 시작
        int endRow = page * limit;
        List<PHDTO> list = mapper.searchPh(phText, startRow, endRow); // HODTO를 PHDTO로 변경, hoText를 phText로 변경
        PaggingVO pagination = getPagination(page, limit, phText); // HOResponseVo를 PHResponseVo로 변경, hoText를 phText로 변경
        return new PHResponseVo(list, pagination); // HOResponseVo를 PHResponseVo로 변경
    }
    
    // 약국 상세 정보를 가져오는 메서드
    public PHDTO selectPhInfo(int phId) { // HODTO를 PHDTO로 변경, hoId를 phId로 변경
        return mapper.selectPhInfo(phId); // HOMapper를 PHMapper로 변경
    }

    // 약국 정보를 삭제하는 메서드
    public void deletePh(int phId) { // hoId를 phId로 변경
        mapper.deletePh(phId); // HOMapper를 PHMapper로 변경
    }

    // 페이지네이션 정보를 가져오는 메서드
    public PaggingVO getPagination(int page, int limit, String phText) { // HOResponseVo를 PHResponseVo로 변경, hoText를 phText로 변경
        int totalCount = phText == null ? mapper.getTotalCount() : mapper.getTotalCountBySearch(phText); // HOMapper를 PHMapper로 변경
        return new PaggingVO(totalCount, page, limit);
    }

	public void insertPh(PHDTO phDTO) {
		mapper.insertPh(phDTO);
		
	}
}
