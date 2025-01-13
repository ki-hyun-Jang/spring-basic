package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.dtos.MemberCreateDto;
import com.beyond.basic.b2_board.dtos.MemberListRes;
import com.beyond.basic.b2_board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

//    의존성주입 방법1. Autowired 어노테이션 사용: 필드 주입

    @Autowired
    private MemberService memberService;

//    홈화면
    @GetMapping("")
    public String memberHome(){
        return "member/home";
    }

//    회원목록 조회
    @GetMapping("/list")
    public String memberList(Model model){
        List<MemberListRes> memberListRes =  memberService.findAll();
        model.addAttribute("memberList",memberListRes);
        return "member/member-list";
    }

//    회원 상세조회
    @GetMapping("/detail/{id}")
    public String memberDetail(@PathVariable Long id, Model model){
        System.out.println(id);
        model.addAttribute("modelId",id);
        return "member/member-detail";
    }

//    회원가입
    @GetMapping("/create")
    public String memberCreate(){
        return "member/member-signup";
    }

    @PostMapping("/create")
    public String memberCreatePost(@ModelAttribute MemberCreateDto memberCreateDto){
        memberService.save(memberCreateDto);
        System.out.println(memberCreateDto);
        return "redirect:/member/list";

    }
}
