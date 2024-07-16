package com.gae.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gae.dto.PHDTO;
import com.gae.service.PHService;
import com.gae.vo.PageVo;

@Controller
public class PHController {
    private final PHService phService;
    
    public PHController(PHService phService) {
        this.phService = phService;
    }
    
    @GetMapping("/ph/main")
    public ModelAndView main(ModelAndView view,
                             @RequestParam(defaultValue = "1") int pageNo,
                             @RequestParam(defaultValue = "20") int pageContentEa) {
        try {
            List<PHDTO> phList = phService.selectPhNewList(pageNo, pageContentEa);
            int totalCount = phService.selectPhTotalCount();
            PageVo vo = new PageVo(totalCount, pageNo, pageContentEa);
            
            view.addObject("list", phList);
            view.addObject("paging", vo); // "pagging" -> "paging" 수정
            
            view.setViewName("main");
        } catch (Exception e) {
            // 예외 처리: 데이터베이스 연동 중 예외 발생 시 처리할 내용
            view.addObject("error", "데이터 조회 중 오류가 발생했습니다.");
            view.setViewName("error"); // 에러 페이지로 이동
        }
        return view;
    }
}
