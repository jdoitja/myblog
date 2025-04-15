package com.example.blog.controller;



import com.example.blog.domain.Article;
import com.example.blog.dto.ArticleViewResponse;
import com.example.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogPageController {

    private final BlogService blogService;

    public BlogPageController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/articles")
    public String getarticles(Model model) {
        List<ArticleViewResponse> articleList = blogService.findArticles()
                .stream().map(ArticleViewResponse::new)
                .toList();


        model.addAttribute("articles", articleList);
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getarticle(@PathVariable Long id, Model model) {

        Article article = blogService.findArticleById(id);

        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }
    @GetMapping("/new-article")
    public String showBlogEditpage(@RequestParam(required = false) Long id, Model model) {
        if(id == null){
            model.addAttribute("article", new ArticleViewResponse());
        } else{
            Article article = blogService.findArticleById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }
        return "newArticle";
    }
}
