package com.example.blog.controller;

import com.example.blog.domain.Article;
import com.example.blog.dto.AddArticleRequest;
import com.example.blog.dto.ArticleResponse;
import com.example.blog.dto.UpdateArticleRequest;
import com.example.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;

    }

    @PostMapping("/api/articles")
    public ResponseEntity<ArticleResponse> saveArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.saveArticle(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(savedArticle.toDto());
        }
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<Article> articles = blogService.findArticles();

        List<ArticleResponse> responseBody = articles.stream().map(article ->
                        new ArticleResponse(article.getId(), article.getTitle(), article.getContent()))
                .toList();
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/api/articles/{id}")
    public Article getArticle(@PathVariable Long id) {
        return blogService.findArticleById(id);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {
        blogService.deleteArticle(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/articles")
    public ResponseEntity<Void> deleteAllArticles() {
        blogService.deleteAllArticles();

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable("id") Long id, @RequestBody UpdateArticleRequest request) {
        Article article = blogService.updateArticle(id, request);
        ArticleResponse articleResponse = article.toDto();
        return ResponseEntity.ok(articleResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return ex.getMessage();
    }

}
