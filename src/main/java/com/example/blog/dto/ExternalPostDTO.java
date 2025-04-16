package com.example.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExternalPostDTO {
    private Long userId;
    private Long id;
    private String title;
    private String body;
}
