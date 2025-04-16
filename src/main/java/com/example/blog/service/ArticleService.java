package com.example.blog.service;

import com.example.blog.domain.Article;
import com.example.blog.domain.Comment;
import com.example.blog.dto.ArticleCommentListDTO;
import com.example.blog.dto.ArticleDTO;
import com.example.blog.dto.CommentDTO;
import com.example.blog.dto.SimpleCommentDTO;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentDTO> getAllCommentsWithArticle() {
        // Comment를 가져오면서 연관된 Article도 함께 로딩
        List<Comment> comments = commentRepository.findAll();

        // CommentDTO로 변환
        return comments.stream()
                .map(CommentDTO::new)  // CommentDTO로 변환하면서 ArticleDTO도 포함됨
                .collect(Collectors.toList());
    }

    public ArticleCommentListDTO getCommentsWithArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        List<Comment> comments = commentRepository.findByArticleId(articleId);

        List<SimpleCommentDTO> commentDTOs = comments.stream()
                .map(SimpleCommentDTO::new)
                .collect(Collectors.toList());

        return new ArticleCommentListDTO(new ArticleDTO(article), commentDTOs);
    }
}