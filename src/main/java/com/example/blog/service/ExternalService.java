package com.example.blog.service;

import com.example.blog.dto.PostContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class ExternalService {

    private static final String URL = "https://jsonplaceholder.typicode.com/posts";

    private final RestTemplate restTemplate = new RestTemplate();

    public void call() {
        // GET 요청 보내기
        ResponseEntity<List<PostContent>> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PostContent>>() {}
        );

        log.info("code: {}", response.getStatusCode());

        List<PostContent> posts = response.getBody();
        if (posts != null) {
            posts.forEach(post -> log.info("Post: {}", post));
        } else {
            log.warn("No posts received from external API");
        }
    }
}
