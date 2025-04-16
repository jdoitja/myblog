package com.example.blog.dto;

import com.example.blog.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SimpleCommentDTO {
    private Long commentId;
    private String body;
    private LocalDateTime createdAt;

    public SimpleCommentDTO(Comment comment) {
        this.commentId = comment.getId();
        this.body = comment.getBody();
        this.createdAt = comment.getCreatedAt();
    }
}