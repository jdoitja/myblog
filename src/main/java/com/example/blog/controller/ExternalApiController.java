package com.example.blog.controller;

import com.example.blog.service.ExternalService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ExternalApiController {

    private final ExternalService externalService;

    public ExternalApiController(ExternalService externalService) {
        this.externalService = externalService;
    }

    @GetMapping("/api/external")
    public ResponseEntity<Void> callExternal() {
        externalService.call();
        return ResponseEntity.ok().build();
    }
}

