package com.beyond.basic.b2_board.repository;

import com.beyond.basic.b2_board.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// SpringDataJpa가 되기 위해서는 JpaRepository를 상속해야하고 상속 시에는 entity명과 entity의 PK타입을 명시
// JpaRepository를 상속함으로서 JpaRepository의 주요기능(각종 CRUD) 상속
public interface MemberRepository extends JpaRepository<Member, Long> {
//    save, findAll, findById는 사전에 구현.
//    그외에 다른컬럼으로 조회할 때는 findBy+컬럼명 형식으로 선언만하면 실행시점 구현.
    Optional<Member> findByEmail(String email);
}
