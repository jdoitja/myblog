package com.example.blog.controller;

import com.example.blog.service.ExternalDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExternalDataController {

    private final ExternalDataService externalDataService;

    @PostMapping("/api/import-articles")
    public ResponseEntity<String> importArticles() {
        externalDataService.fetchAndSaveArticles();
        return ResponseEntity.ok("게시글 데이터 저장 완료!");
    }
}