package com.gae.controller;

import com.gae.dto.HODTO;
import com.gae.service.HOService;
import com.gae.vo.PageVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HOController {

    private final HOService hoService;

    public HOController(HOService hoService) {
        this.hoService = hoService;
    }

    @GetMapping("/ho/main")
    public String main(Model model,
                       @RequestParam(defaultValue = "1") int pageNo,
                       @RequestParam(defaultValue = "20") int pageContentEa) {
        // 해당 페이지 게시글 목록 읽어옴
        List<HODTO> hoList = hoService.selectHoNewList(pageNo, pageContentEa);
        // 전체 게시글 수
        int totalCount = hoService.selectHoTotalCount();
        // 페이징 정보 생성
        PageVo vo = new PageVo(totalCount, pageNo, pageContentEa);

        // 데이터를 모델에 추가
        model.addAttribute("list", hoList);
        model.addAttribute("paging", vo);

        return "main";
    }

    // 게시글 한 건 조회하는 메서드
    @GetMapping("/ho/{hno}")
    public String boardView(@PathVariable int hno, Model model) {
        HODTO hospital = hoService.selectHo(hno);
        model.addAttribute("hospital", hospital);
        return "ho_view"; // ho_view.html의 이름
    }
}
