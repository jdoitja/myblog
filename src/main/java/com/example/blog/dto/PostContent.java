package com.example.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostContent {
    private int userId;
    private int id;
    private String title;
    private String body;

    @Override
    public String toString() {
        return "PostContent [id=" + id + title;
    }
}
