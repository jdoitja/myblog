package com.example.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class UpdateArticleRequest {
    private String title;
    private String content;

    public UpdateArticleRequest(String modifiedTitle, String modifiedContent) {
        this.title = modifiedTitle;
        this.content = modifiedContent;

    }
}
