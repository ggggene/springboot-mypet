/*
package com.petposting.springbootmypet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petposting.springbootmypet.domain.Article;
import com.petposting.springbootmypet.dto.AddArticleRequest;
import com.petposting.springbootmypet.dto.UpdateArticleRequest;
import com.petposting.springbootmypet.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc // MockMvc(가상 Mvc 객체) 생성 및 자동 구성
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {

        // given: 블로그 글 추가에 필요한 요청 객체를 만듬
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when : 블로그 글 추가 API에 json 요청 타입으로 given의 객체를 요청 본문으로 보냄.
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url) // URL 요청
                .contentType(MediaType.APPLICATION_JSON_VALUE) // json, xml 등 다양한 타입 중 하나로 요청 보냄.
                .content(requestBody));

        // then : blog를 전체 조회해 크기(블로그 글 수)가 1인지 확인 및 실제 저장 데이터와 요청 값 비교
        // 응답 코드가 201 Created인지 확인.
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1); // 크기(블로그 글 개수)가 1인지 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticle: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception {
        //given: 블로그 글 저장
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";


        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when: 목록 조회 API 호출
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then: 반환값 중에 0번째 요소의 content와 title이 저장된 값과 같은지 확인.
        // 응답 코드가 200 ok 확인
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }


    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        // given: 블로그 글 저장
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
               .title(title)
               .content(content)
               .build());

        // when: 저장한 블로그 글의 id 값을 API를 호출
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then: 응답 코드가 200 ok이고, 반환값 content와 title이 저장된 값과 같은지 확인
        resultActions
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content").value(content))
               .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        // given: 블로그 글을 저장
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
               .title(title)
               .content(content)
               .build());

        // when: 저장한 블로그 글의 id값으로 삭제API 호출
        mockMvc.perform(delete(url, savedArticle.getId()))
                        .andExpect(status().isOk());

        // then: 블로그 글 리스트를 전체 조회해 조회한 배열 크기가 0인지 확인
        // id에 해당하는 글 삭제 검증
        // 응답 코드가 200 ok 확인
        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        // given: 블로그 글을 저장하고, 블로그 글 수정에 필요한 요청 객체 생성
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
               .title(title)
               .content(content)
               .build());

        final String newTitle = "new title";
        final String newContent = "new content";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when: UPDATE API로 수정 요청을 보냅니다.
        // 요청 타입은 JSON이며, given절에서 미리 만들어둔 객체를 요청 본문으로 함께 보냄.
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then: 블로그 글 id로 조회한 후에 값 수정 확인.
        // 응답 코드 200 ok인지 확인
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

    }

}*/
