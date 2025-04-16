package com.example.blog.controller;

import com.example.blog.service.ExternalDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExternalCommentController {

    private final ExternalDataService externalDataService;

    // /import 요청이 오면 외부 API에서 데이터를 가져와서 DB에 저장
    @PostMapping("/api/external-comments/import")
    public ResponseEntity<String> importExternalComments() {
        externalDataService.fetchAndSaveExternalComments();
        return ResponseEntity.ok("External Comments data imported successfully!");
    }
}
