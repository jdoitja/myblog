package com.example.blog.controller;

import com.example.blog.domain.Article;
import com.example.blog.domain.Book;
import com.example.blog.dto.*;
import com.example.blog.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;

    }
    @RequestMapping(method = RequestMethod.GET, value = "/books")
    public String showBooks(Model model) {
        //조회
        List<BookViewResponse> bookList =  bookService.getBookList()
                .stream()
                .map(BookViewResponse::new)
                .toList();

        model.addAttribute("bookList", bookList);
        return "bookManage";
    }

    @RequestMapping(method = RequestMethod.GET, path ="/books/{id}")
    public String showBookDetail(@PathVariable("id") String id, Model model) {

        Book book = bookService.getBookById(id);

        model.addAttribute("book", new BookViewResponse(book));

        return "bookDetail";
    }

    @PostMapping("/books")
    public String addBook(@ModelAttribute("book") AddBookRequest request) {
        bookService.saveBook(request);
        return "redirect:/books";
    }
}
