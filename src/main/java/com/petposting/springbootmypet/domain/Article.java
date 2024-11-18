package com.petposting.springbootmypet.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity // 엔티티로 지정
@Getter // getter 접근자 메서드 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자
public class Article {

    // 일련번호
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩증가(= auto_increment)
    @Column(name = "id", updatable = false) // 'id'라는 수정불가한 컬럼과 매핑
    private Long id; // Article 테이블의 'id' 컬럼과 매핑

    // 게시물 제목
    @Column(name = "title", nullable = false) // 'title'이라는 not null 컬럼과 매핑
    private String title;

    // 게시물 내용
    @Column(name = "content", nullable = false) // 'content'이라는 not null 컬럼과 매핑
    private String content;

    @Column(name = "author", nullable = false)
    private String author;

    @CreatedDate // 엔티티가 생성될 때 created_at 컬럼에 생성 시간 저장
    @Column(name = "created_at") // created_at 컬럼과 매핑
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 updated_at 컬럼에 수정 시간 저장
    @Column(name = "updated_at") // updated_at 컬럼과 매핑
    private LocalDateTime updatedAt;

    @Builder // 빌더 패턴으로 객체 생성
    public Article(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }


    // 엔티티에 요청 받은 내용으로 값을 수정하는 메서드
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }


}
