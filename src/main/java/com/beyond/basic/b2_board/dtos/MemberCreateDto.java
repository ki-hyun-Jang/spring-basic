package com.beyond.basic.b2_board.dtos;

import com.beyond.basic.b2_board.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateDto {
    private String name;
    private String email;
    private String password;



    public Member toEntity(){
        return new Member(this.name, this.email, this.password);
    }
}
