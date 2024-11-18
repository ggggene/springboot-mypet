package com.petposting.springbootmypet.controller;

import com.petposting.springbootmypet.domain.Article;
import com.petposting.springbootmypet.dto.AddArticleRequest;
import com.petposting.springbootmypet.dto.ArticleResponse;
import com.petposting.springbootmypet.dto.UpdateArticleRequest;
import com.petposting.springbootmypet.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;

    // HTTP 메서드가 POST일 때 전달 받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        Article savedArticle = blogService.save(request, principal.getName());

        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    // get 요청 시 글 전체를 조회하는 findAll() 메서드 실행
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream() // 여러 데이터가 모여 있는 컬렉션을 간편하게 처리
                .map(ArticleResponse::new) // 응답용 객체로 파싱
                .toList();

        return ResponseEntity.ok()
                .body(articles); // body에 담아 클라이언트에게 전송
    }

    // GET 요청 시 블로그 글을 조회하기 위한 매핑할 findArticle 메서드
    @GetMapping("/api/articles/{id}")
    // url 경로에서 값 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id){
        // @PathVariable: URL에서 값을 가져오는 애너테이션({id}에 들어온 값을 파라미터 id로 사용)
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
                // id에 해당하는 글의 정보를 body에 담아 브라우저에 전송
    }

    // DELETE 요청 시 글을 삭제하는 findArticles() 메서드
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        // {id}에 해당하는 값이 @PathVariable 애너테이션을 통해 파라미터 id로 들어옴.
        blogService.delete(id);

        return ResponseEntity.ok().build();
    }

    // PUT 요청 시 글을 수정하는 updateArticle() 메서드
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request){
        // PUT 요청 시 RequestBody 정보가 request로 넘어옴.
        // 다시 service 클래스의 update() 메서드에 id와 request를 넘김
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle); // 응답 값을 body에 담아 전송.
    }


}
