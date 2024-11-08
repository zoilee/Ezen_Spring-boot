package com.zoile.kdtboot.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.zoile.kdtboot.dto.BbsDto;
import com.zoile.kdtboot.service.BbsService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BbsService bbsService;

    @GetMapping({"/", "/index"})
    public String index(Model model,
        @RequestParam(name="page", defaultValue="0") int page,
        @RequestParam(name="size", defaultValue="15") int size,
        @RequestParam(name="searchKey", required = false) String searchKey,
        @RequestParam(name="searchVal", required = false) String searchVal
    ){
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("num").descending());
        Page<BbsDto> bbsPage;
        if((searchKey != null && !searchKey.isEmpty()) && (searchVal != null && !searchVal.isEmpty())){
            bbsPage = bbsService.search(searchKey, searchVal, pageable);
            model.addAttribute("searchKey", searchKey);
            model.addAttribute("searchVal", searchVal);
        }else{
            bbsPage = bbsService.findAll(pageable);
        }
        /** 페이지 그룹 미리 계산 */
        int totalPages = bbsPage.getTotalPages();
        int currentPage = bbsPage.getNumber() +1;
        int startPageOfGroup = ((currentPage - 1)/ 15 )* 15 +1;
        int endPageOfGroup = Math.min(startPageOfGroup + 14, totalPages);

        model.addAttribute("bbsPage", bbsPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPageOfGroup", startPageOfGroup);
        model.addAttribute("endPageOfGroup", endPageOfGroup);
        model.addAttribute("totalPages", totalPages);
        return "index";
    }

    @GetMapping("/view/{num}")
    public String findById(@PathVariable("num") Long num, Model model) {
        /*
         * 1. 조회수를 올린다.
         * 2. id를 이용해 view.html을 연다
         */
        bbsService.updateCount(num);
        BbsDto dto = bbsService.findById(num);
        model.addAttribute("bbs", dto);
        return "view";
    }
    
    @GetMapping("/delete/{num}")
    public String getMethodName(@PathVariable("num") Long num, Model model) {
        
        bbsService.deleteById(num);
        
        return "redirect:/index";
    }
    



    @GetMapping("/write")
    public String write() {
        return "write";
    }
    
    @PostMapping("/write")
    public String writeForm(@ModelAttribute BbsDto dto){
        System.out.println("save()");
        bbsService.save(dto);
        return "redirect:index";

    }
}
