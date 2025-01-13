package com.beyond.basic.b2_board.dtos;

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
}
