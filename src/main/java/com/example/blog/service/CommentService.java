package com.example.blog.service;

import com.example.blog.domain.Article;
import com.example.blog.domain.Comment;
import com.example.blog.dto.ArticleCommentListDTO;
import com.example.blog.dto.ArticleDTO;
import com.example.blog.dto.CommentDTO;
import com.example.blog.dto.SimpleCommentDTO;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    // 댓글 생성
    public CommentDTO createComment(Long articleId, String body) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        Comment comment = new Comment(article, body);  //댓글 생성
        commentRepository.save(comment);
        return new CommentDTO(comment);  // 생성된 댓글 DTO 반환
    }

    // 댓글 조회 (articleId와 commentId로 조회)
    public CommentDTO getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return new CommentDTO(comment);
    }

    public CommentDTO updateComment(Long commentId, String newBody) {
        // commentId로 댓글을 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment with commentId " + commentId + " not found"));

        // 댓글 내용 수정
        comment.setBody(newBody);

        // 댓글 수정 후 저장
        Comment updatedComment = commentRepository.save(comment);

        // 수정된 댓글을 DTO로 변환하여 반환
        return new CommentDTO(updatedComment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }

    public ArticleCommentListDTO getCommentsWithArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        List<Comment> comments = commentRepository.findByArticleId(articleId);

        List<SimpleCommentDTO> commentDTOs = comments.stream()
                .map(SimpleCommentDTO::new)
                .collect(Collectors.toList());

        return new ArticleCommentListDTO(new ArticleDTO(article), commentDTOs);
    }
}