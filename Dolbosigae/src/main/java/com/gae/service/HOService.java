package com.gae.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gae.dto.HODTO;
import com.gae.mapper.HOMapper;

@Service
public class HOService {
	private HOMapper mapper;
	
	public HOService(HOMapper mapper) {
		this.mapper= mapper;
	}
	
	public List<HODTO> selectHoNewList(int pageNo, int pageContentEa) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", pageNo);
		map.put("pageContentCount", pageContentEa);
		return mapper.selectHoNewList(map);
	}
	
	public int selectHoTotalCount() {
		return mapper.selectHoTotalCount();
	}

	public HODTO selectHo(int hno) {
		return mapper.selectHO(hno);
	}

	
}
