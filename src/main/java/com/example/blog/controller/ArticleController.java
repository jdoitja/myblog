package com.example.blog.controller;

import com.example.blog.dto.CommentDTO;
import com.example.blog.service.ArticleService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/api/articles")
    public List<CommentDTO> showComments() {
        List<CommentDTO> commentDTOs = articleService.getAllCommentsWithArticle();
        return commentDTOs;
    }
}