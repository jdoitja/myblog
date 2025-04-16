package com.example.blog.service;

import com.example.blog.domain.Article;
import com.example.blog.domain.ExternalComment;
import com.example.blog.dto.ExternalCommentDTO;
import com.example.blog.dto.ExternalPostDTO;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.ExternalCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExternalDataService {

    private final ArticleRepository articleRepository;
    private final ExternalCommentRepository externalCommentRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    // 게시글 데이터를 가져와서 DB에 저장
    public void fetchAndSaveArticles() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        ResponseEntity<ExternalPostDTO[]> response = restTemplate.getForEntity(url, ExternalPostDTO[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            ExternalPostDTO[] externalPosts = response.getBody();

            if (externalPosts != null) {
                List<Article> articles = Arrays.stream(externalPosts)
                        .map(dto -> {
                            Article article = new Article();
                            article.setTitle(dto.getTitle());
                            article.setContent(dto.getBody());
                            return article;
                        })
                        .collect(Collectors.toList());

                articleRepository.saveAll(articles);
            }
        }
    }

    // 댓글 데이터를 가져와서 DB에 저장
    public void fetchAndSaveExternalComments() {
        String url2 = "https://jsonplaceholder.typicode.com/comments";

        try {
            ResponseEntity<ExternalCommentDTO[]> responseEntity = restTemplate.exchange(
                    url2, HttpMethod.GET, null, ExternalCommentDTO[].class);

            ExternalCommentDTO[] externalCommentDTOs = responseEntity.getBody();

            // 데이터가 null이면 종료
            if (externalCommentDTOs == null || externalCommentDTOs.length == 0) {
                System.out.println("No external comments received from API.");
                return; // 받은 데이터가 없다면 종료
            }

            // DB에 저장
            for (ExternalCommentDTO dto : externalCommentDTOs) {
                ExternalComment externalComment = new ExternalComment();
                externalComment.setPostId(dto.getPostId());
                externalComment.setName(dto.getName());
                externalComment.setEmail(dto.getEmail());
                externalComment.setBody(dto.getBody());

                externalCommentRepository.save(externalComment); // JPA로 DB에 저장
            }

        } catch (Exception e) {
            // 예외 처리
            System.out.println("Error fetching or saving external comments: " + e.getMessage());
            e.printStackTrace();
        }
    }
}