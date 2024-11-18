package com.petposting.springbootmypet.repository;

import com.petposting.springbootmypet.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogRepository extends JpaRepository<Article, Long> { // 스프링 데이터 JPA에서 제공하는 인터페이스인 JpaRepository클래스 상속
}
