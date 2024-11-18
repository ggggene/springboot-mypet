package com.petposting.springbootmypet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest { // 회원 추가를 위한 사용자 정보를 담는 DTO 객체
    private String email;
    private String password;
}

