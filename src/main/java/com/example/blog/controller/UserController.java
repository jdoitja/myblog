package com.example.blog.controller;

import com.example.blog.dto.AddUserRequest;
import com.example.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public String signup(@ModelAttribute AddUserRequest request) {
        userService.signUp(request);  // 회원 가입(저장)
        return "redirect:/login";   // 회원 가입 처리 후 로그인 페이지로 강제 이동
    }

    @PostMapping("/logoutPage")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        new SecurityContextLogoutHandler().logout(request, response, authentication);

        return "redirect:/login";

        }
}
