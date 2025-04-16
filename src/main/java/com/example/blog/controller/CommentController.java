package com.example.blog.controller;

import com.example.blog.dto.ArticleCommentListDTO;
import com.example.blog.dto.CommentDTO;
import com.example.blog.dto.CommentRequest;
import com.example.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    // 댓글 생성 (POST /api/articles/{articleId}/comments)
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long articleId,
                                                    @RequestBody CommentRequest request) {
        // 댓글 생성 서비스 호출
        CommentDTO createdComment = commentService.createComment(articleId, request.getBody());
        return ResponseEntity.ok(createdComment);  // 생성된 댓글 반환
    }

    // 댓글 조회 (GET /api/articles/{articleId}/comments/{commentId})
    @GetMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long commentId) {
        CommentDTO commentDTO = commentService.getCommentById(commentId);
        return ResponseEntity.ok(commentDTO);
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentRequest request) {
        CommentDTO updatedComment = commentService.updateComment(commentId, request.getBody());
        return ResponseEntity.ok(updatedComment);  // 수정된 댓글을 DTO로 반환
    }

    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<ArticleCommentListDTO> getCommentsByArticle(@PathVariable Long articleId) {
        ArticleCommentListDTO response = commentService.getCommentsWithArticle(articleId);
        return ResponseEntity.ok(response);
    }

}
