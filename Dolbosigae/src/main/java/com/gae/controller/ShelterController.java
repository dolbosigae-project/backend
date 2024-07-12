package com.gae.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gae.dto.ShelterDTO;
import com.gae.service.ShelterService;
import com.gae.vo.PageVo;

@Controller
public class ShelterController {

    private ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }
    
    @GetMapping("/shelter")
    public ModelAndView shelter(ModelAndView view,
                                @RequestParam(defaultValue = "1") int pageNo,
                                @RequestParam(defaultValue = "20") int pageContentEa) {
        // 해당 페이지 게시글 목록 읽어옴
        List<ShelterDTO> shelterList = shelterService.selectShelterList(pageNo, pageContentEa);

        // 전체 게시글 수 읽어옴
        int totalCount = shelterService.selectShelterTotalCount();

        // 페이징 정보 생성
        PageVo vo = new PageVo(totalCount, pageNo, pageContentEa);

        // request 영역에 저장
        view.addObject("list", shelterList);
        view.addObject("pagging", vo);

        // 뷰 이름 설정
        view.setViewName("shelter");
        return view;
    }
}
