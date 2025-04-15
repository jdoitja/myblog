package com.example.blog.dto;

import com.example.blog.domain.Article;
import com.example.blog.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BookViewResponse {
    private String id;
    private String name;
    private String author;

    public BookViewResponse(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
    }
}
