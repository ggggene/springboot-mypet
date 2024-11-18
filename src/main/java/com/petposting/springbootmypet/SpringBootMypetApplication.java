package com.petposting.springbootmypet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 스프링부트를 실행하기 위한 클래스
@EnableJpaAuditing // created_at, updated_at 자동 업데이트
@SpringBootApplication // 스프링부트에 필요한 기본설정 애너테이션
public class SpringBootMypetApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMypetApplication.class, args); // 애플리케이션 실행 메서드
    }
}
