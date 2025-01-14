package com.beyond.basic.b2_board.service;

import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.dtos.MemberCreateDto;
import com.beyond.basic.b2_board.dtos.MemberDetailDto;
import com.beyond.basic.b2_board.dtos.MemberListRes;
import com.beyond.basic.b2_board.repository.MemberJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
// 아래 어노테이션을 통해, 모든 메서드에 트랜잭션을 적용하고, 만약 예외(uncheck)가 발생 시 롤백처리 자동화
// 롤백은 uncheck
@Transactional
public class MemberService {

    @Autowired
    private MemberJdbcRepository memberRepository;

    public List<MemberListRes> findAll(){
//        List<Member> members = memberRepository.findAll();
//        List<MemberListRes> result = new ArrayList<>();
//        for(Member m : members){
//            result.add(m.listFromEntity());
//        }
        return memberRepository.findAll()
                .stream()
                .map(Member::listFromEntity)
                .collect(Collectors.toList());
    }

    public void save(MemberCreateDto memberCreateDto) throws Exception{
        String email = memberCreateDto.getEmail();
        String password = memberCreateDto.getPassword();
        if(password.length() < 8 ){
            throw new Exception("비밀번호가 너무 짧습니다. 8자리 이상으로 설정해주세요.");
        } else if (memberRepository.findByEmail(email).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }
        memberRepository.save(memberCreateDto.toEntity());
    }

    public MemberDetailDto findById(Long id) throws NoSuchElementException, RuntimeException{
//        Optional<Member> optionalMember = memberRepository.findById(id);
//        Member member = optionalMember.orElseThrow(()-> new NoSuchElementException("없는 아이디입니다."));
//        return member.detailFromEntity();
        return memberRepository.findById(id)
                .orElseThrow(()-> new RuntimeException())
                .detailFromEntity();

    }
}
