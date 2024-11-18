package com.petposting.springbootmypet.controller;

import com.petposting.springbootmypet.dto.CreateAccessTokenRequest;
import com.petposting.springbootmypet.dto.CreateAccessTokenResponse;
import com.petposting.springbootmypet.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;

    // API 요청 시 토큰 서비스에서 리프레시 토큰 기반인 새로운 엑세스 토큰을 생성 처리 컨트롤러
    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
