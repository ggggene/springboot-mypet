package com.petposting.springbootmypet.controller;

import com.petposting.springbootmypet.domain.Article;
import com.petposting.springbootmypet.dto.ArticleListViewResponse;
import com.petposting.springbootmypet.dto.ArticleViewResponse;
import com.petposting.springbootmypet.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    // 블로그 글 목록을 반환하는 메서드
    @GetMapping("/articles")
    public String getArticles(Model model){
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();
        /*
        ArticleListViewResponse::new -> ArticleListViewResponse 생성자를 참조하는 메서드 참조 표현
        map 메서드는 스트림의 각 요소를 변환하기 위해 사용
        stream()메서드를 사용하여 Java Stream API를 통해 처리 -> 데이터를 필터링, 매핑, 수집하는 등의 다양한 작업을 함수형 스타일로 간편하게 처리
        toList()메서드로 스트림을 통해 변환된 요소들을 다시 리스트로 모아 최종적으로 List<ArticleListViewResponse> 형태의 리스트를 반환
        */
        model.addAttribute("articles", articles); // 블로그 글 리스트를 모델객체에 저장

        return "articleList"; // articleList.html이라는 view 조회
    }

    // 블로그 상세글을 반환하는 컨트롤러 메서드
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model){ // url {id}의 값을 id 인자로 받아 상세글 조회
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article)); // 모델 객체에 글 데이터 저장

        return "article"; // article.html에 반환. view 조회
    }

    // 블로그 글 수정과 생성하는 뷰 컨트롤러
    @GetMapping("/new-article")
    // id 키를 가진 쿼리 파라미터(RequestParam)의 값을 id 변수에 매핑(id는 없을 수도 있음)
    // required = false: 요쳥값이 반드시 존재하지 않아도 된다는 의미
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) { // id가 없으면 생성
            // ArticleViewResponse의 기본 생성자를 이용해 빈ArticleViewResponse객체 생성
            model.addAttribute("article", new ArticleViewResponse());
        }else { // id가 있으면 수정
            // 기존 값을 가져오는 findById() 메서드 호출
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle"; // newArticle.html view 조회
    }
}
