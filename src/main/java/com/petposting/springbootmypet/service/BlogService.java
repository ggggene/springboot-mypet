package com.petposting.springbootmypet.service;

import com.petposting.springbootmypet.domain.Article;
import com.petposting.springbootmypet.dto.AddArticleRequest;
import com.petposting.springbootmypet.dto.UpdateArticleRequest;
import com.petposting.springbootmypet.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
        // JpaRepository에서 지원하는 저장 메서드 save()로 dto 객체인 AddArticleRequest클래스에 저장된 값을 article DB에 저장함.
    }

    // DB에 저장된 모든 블로그 글 조회 메서드
    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    // 블로그 글 하나를 조회 메서드
    public Article findById(Long id){
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
                // JPA에서 제공하는 findById() 메서드를 사용해 id를 받아 엔티티를 조회하고 없으면 IllegalArgumentException 예외 발생시킴.
    }

    //ID에 해당하는 블로그 글 삭제 API 메서드
    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    // 글을 수정하는 update() 메서드
    // 트랜잭션 메서드: 매칭한 메서드를 하나의 트랜잭션으로 묶음.
    // 여러 작업을 하나의 트랜잭션 단위로 묶으면, 엔티티의 필드 값이 바뀌어 에러가 발생해도 안전한 값 수정을 보장.
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}
