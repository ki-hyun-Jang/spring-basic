package com.beyond.basic.b2_board.domain;

import com.beyond.basic.b2_board.dtos.MemberDetailDto;
import com.beyond.basic.b2_board.dtos.MemberListRes;
import com.beyond.basic.b2_board.dtos.MemberUpdatePasswordDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
@Entity // jpa의 엔티티매니저에게 객체를 위임하려면 @Entity 어노테이션이 필요하다.
public class Member {
    @Id // pk 설정 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment 설정 (GenerationType이 Auto면 jpa에게 적절한 전략을 위임하는 것)
    private Long id;
    private String name; // String은 default가 varchar255로 DB컬럼에 설정. 변수명==컬럼명으로 변환.
    @Column(length = 50, unique = true, nullable = false)
    private String email;
//    @Column(name = "pw") 컬럼명을 변경 가능. 하지만 변수명과 컬럼명을 일치시키는 것이 개발의 혼선 감소.
    private String password;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;

    public Member(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public MemberListRes listFromEntity(){
        return new MemberListRes(this.id, this.name, this.email);
    }

    public MemberDetailDto detailFromEntity() {
        return new MemberDetailDto(this.name, this.email, this.password);
    }

    public void updatePw(String newPw){
        this.password = newPw;
    }
}
