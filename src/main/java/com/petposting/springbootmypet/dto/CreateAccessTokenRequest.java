package com.petposting.springbootmypet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenRequest { // 토큰 생성 요청 DTO
    private String refreshToken;
}
