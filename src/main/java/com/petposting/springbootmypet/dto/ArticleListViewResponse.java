package com.petposting.springbootmypet.dto;

import com.petposting.springbootmypet.domain.Article;
import lombok.Getter;

// 블로그 글목록 데이터 전달
@Getter
public class ArticleListViewResponse { // view에 데이터를 전달하기 위한 객체

    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content =  article.getContent();
    }
}
