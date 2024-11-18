package com.petposting.springbootmypet.dto;

import com.petposting.springbootmypet.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class AddArticleRequest { // 단순히 데이터를 옮기기 위한 전달자 역할의 DTO 객체

    private String title;
    private String content;

    public Article toEntity(String author) { // 생성자를 사용해 객체 생성, toEntity는 빌더 패턴을 사용해 DTO를 엔티티로 만드는 메서드
        // 글 추가 시 저장할 엔티티로 변환해주는 용도
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
