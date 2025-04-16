package com.example.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExternalCommentDTO {

    private Long postId;
    private String name;
    private String email;
    private String body;
}
