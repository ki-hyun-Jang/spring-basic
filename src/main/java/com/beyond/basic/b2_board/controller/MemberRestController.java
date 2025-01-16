package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.dtos.*;
import com.beyond.basic.b2_board.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequestMapping("/member/rest")
@RestController
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> memberList(){
        List<MemberListRes> memberList = memberService.findAll();
        return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(), "memberList is found.", memberList),HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> memberDetail(@PathVariable Long id){
        MemberDetailDto dto;
        try {
            dto =  memberService.findById(id);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(new CommonErrorDto(HttpStatus.NOT_FOUND.value(),e.getMessage()),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(), "member is found.",dto),HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> memberCreatePost(@RequestBody MemberCreateDto memberCreateDto){
        Member member;
        try {
            member = memberService.save2(memberCreateDto);
        }catch (Exception e){
            return new ResponseEntity<>(new CommonErrorDto(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new CommonDto(HttpStatus.CREATED.value(), "아이디 잘 만들어졌다.",member.getId()),HttpStatus.CREATED);
    }

//    get: 조회, post: 등록, patch: 부분수정, put: 대체, delete: 삭제
//    프론트에서 axios.patch 해야함
    @PatchMapping("/update/pw")
    public ResponseEntity<?> memberUpdatePassword(@RequestBody MemberUpdatePasswordDto memberUpdatePasswordDto){
        try{
            memberService.memberUpdatePassword(memberUpdatePasswordDto);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(new CommonErrorDto(HttpStatus.NOT_FOUND.value(),e.getMessage()),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(), "비밀번호 잘바뀌었다.","잘바뀜"),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id){
        try {
            memberService.delete(id);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(new CommonErrorDto(HttpStatus.NOT_FOUND.value(),e.getMessage()),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(), "삭제 잘 되었다.","잘 삭제됨"),HttpStatus.OK);
    }



}
