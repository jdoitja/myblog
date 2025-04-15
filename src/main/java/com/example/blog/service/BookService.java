package com.example.blog.service;

import com.example.blog.domain.Book;
import com.example.blog.dto.AddBookRequest;
import com.example.blog.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBookList() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    public Book saveBook(AddBookRequest request) {
        Book book = request.toEntity();
        return bookRepository.save(book);
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Book not found"));
    }
}
