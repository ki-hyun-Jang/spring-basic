package com.beyond.basic.b1_hello.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 매개변수있는 생성자
//@Getter // (class 위에 작성했다면) 모든 필드가 있는 getter 필드위에 작성하면 특정 필드만 적용
@Data//: getter setter, toString까지 포함하는 어노테이션
public class Hello {
    private String name;
    private String email;
}
