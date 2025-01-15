package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.dtos.MemberCreateDto;
import com.beyond.basic.b2_board.dtos.MemberDetailDto;
import com.beyond.basic.b2_board.dtos.MemberListRes;
import com.beyond.basic.b2_board.dtos.MemberUpdatePasswordDto;
import com.beyond.basic.b2_board.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/member/rest")
@RestController
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/list")
    public List<MemberListRes> memberList(){
        return memberService.findAll();
    }

    @GetMapping("/detail/{id}")
    public MemberDetailDto memberDetail(@PathVariable Long id){
        MemberDetailDto dto =  memberService.findById(id);
        return dto;
    }

    @PostMapping("/create")
    public String memberCreatePost(@RequestBody MemberCreateDto memberCreateDto){
        try {
            memberService.save(memberCreateDto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ok";
    }

//    get: 조회, post: 등록, patch: 부분수정, put: 대체, delete: 삭제
//    프론트에서 axios.patch 해야함
    @PatchMapping("/update/pw")
    public String memberUpdatePassword(@RequestBody MemberUpdatePasswordDto memberUpdatePasswordDto){
        memberService.memberUpdatePassword(memberUpdatePasswordDto);
        return "ok";
    }


}
