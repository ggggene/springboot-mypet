package com.petposting.springbootmypet.service;

import com.petposting.springbootmypet.domain.User;
import com.petposting.springbootmypet.dto.AddUserRequest;
import com.petposting.springbootmypet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService { // 회원 정보 추가 서비스

    private final UserRepository userRepository;

    public Long save(AddUserRequest dto) { // 회원정보 추가 메서드
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                // 패스워드 암호화
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }

    // 전달받은 userID로 유저 검색하는 메서드
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    // 이메일을 입력 받고 users테이블에서 유저를 찾고, 없을 시 예외 발생시키는 메서드
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
