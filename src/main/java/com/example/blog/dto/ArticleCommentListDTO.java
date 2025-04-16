package com.example.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;


@Getter
@AllArgsConstructor
public class ArticleCommentListDTO {
    private ArticleDTO article;
    private List<SimpleCommentDTO> comments;
}