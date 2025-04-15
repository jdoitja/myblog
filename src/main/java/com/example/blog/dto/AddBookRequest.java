package com.example.blog.dto;

import com.example.blog.domain.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBookRequest {
    private String id;
    private String name;
    private String author;

    public Book toEntity() {
        return new Book(id, name, author);
    }
}
