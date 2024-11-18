package com.petposting.springbootmypet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController { // 로그인 회원가입 페이지 연결 컨트롤러

    // 로그인 페이지 뷰 조회
    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    // 회원가입 페이지 뷰 조회
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}
