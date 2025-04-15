package com.example.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/login")
    public String loginView(){
        return "login";
    }

    @GetMapping("/signup")
    public String signupView(){
        return "signup";
    }


}
