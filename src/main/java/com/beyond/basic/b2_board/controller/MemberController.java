package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.dtos.MemberCreateDto;
import com.beyond.basic.b2_board.dtos.MemberDetailDto;
import com.beyond.basic.b2_board.dtos.MemberListRes;
import com.beyond.basic.b2_board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

//    의존성주입 방법1. Autowired 어노테이션 사용: 필드 주입
//    @Autowired
//    private MemberService memberService;

//    의존성주입 방법2. 생성자 주입방식(가장 많이 사용하는 방식)
//    장점1. final을 통해 상수로 사용이 가능하다. (안전성 향상)
//    장점2. 다형성 구현 가능
//    장점3. 순환참조 컴파일타임에 체크가 가능하다.

    private final MemberService memberService;
//    싱글톤 객체로 만들어지는 시점에 아래 생성자가 호출. 생성자가 하나밖에 없을 때는 Autowired 생략가능
//    @Autowired
//    public MemberController(MemberService memberService){
//        this.memberService = memberService;
//    }

//    의존성주입방법3. RequiredArgsConstructor 어노테이션 활용
//    @RequiredArgsConstructor: 반드시 초기화되어야하는 필드(final, @Nonnull)를 대상으로 생성자를 자동으로 만들어주는 어노테이션
//    객체지향의 특성인 다형성을 활용할 수 없다.

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
        try {
            MemberDetailDto dto =  memberService.findById(id);
            model.addAttribute("member",dto);
        }catch (NoSuchElementException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/member-error";
        }catch (RuntimeException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/member-error";
        }
        return "member/member-detail";
    }

//    회원가입
    @GetMapping("/create")
    public String memberCreate(){
        return "member/member-signup";
    }

    @PostMapping("/create")
    public String memberCreatePost(@ModelAttribute MemberCreateDto memberCreateDto, Model model){
        try {
            memberService.save(memberCreateDto);
        }catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/member-error";
        }
        return "redirect:/member/list";

    }
}
