package com.petposting.springbootmypet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateAccessTokenResponse { // 토큰 생성 응답 DTO
    private String accessToken;
}
