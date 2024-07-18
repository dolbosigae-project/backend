package com.gae.service;

import com.gae.dto.HODTO;
import com.gae.mapper.HOMapper;
import com.gae.vo.HOResponseVo;
import com.gae.vo.PaggingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HOService {
    private final HOMapper mapper;

    @Autowired
    public HOService(HOMapper mapper) {
        this.mapper = mapper;
    }

    public List<HODTO> searchHo(int pageNo, int pageContentEa) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNo);
        map.put("pageContentCount", pageContentEa);
        return mapper.getHoList(map);
    }

    public int selectTotalHoCount() {
        return mapper.selectHo().size();  // Assuming this method returns the total count
    }

    public HODTO selectHo(int hno) {
        return mapper.searchByHoId(String.valueOf(hno));
    }

    public HOResponseVo getRegionList(int page, int pageSize) {
        int startRow = (page - 1) * pageSize;
        int endRow = page * pageSize;

        Map<String, Object> params = new HashMap<>();
        params.put("startRow", startRow);
        params.put("endRow", endRow);

        List<HODTO> hoList = mapper.getHoList(params);
        int totalHoCount = mapper.selectHo().size();  // Assuming this method returns the total count

        PaggingVO pagination = new PaggingVO(totalHoCount, page, pageSize);
        return new HOResponseVo(hoList, pagination);
    }
}
