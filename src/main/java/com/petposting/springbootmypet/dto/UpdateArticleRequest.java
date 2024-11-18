package com.petposting.springbootmypet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateArticleRequest { // 글 수정 요청 받을 DTO 객체
    private String title;
    private String content;
}
