package com.example.blog.repository;

import com.example.blog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BlogRepository extends JpaRepository<Article, Long> {

}
