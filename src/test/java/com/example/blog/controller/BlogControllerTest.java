package com.example.blog.controller;

import com.example.blog.domain.Article;
import com.example.blog.dto.AddArticleRequest;
import com.example.blog.dto.UpdateArticleRequest;
import com.example.blog.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        blogRepository.deleteAll();

    }

    @Test
    void saveArticle()throws Exception{
        // given : Object - > json (ObjectMapper) 직렬화
        AddArticleRequest request = new AddArticleRequest("제목","내용");
        String requestBody = objectMapper.writeValueAsString(request);
        System.out.println("requestBody: " + requestBody);
        //String requestBody ="""
        //        {
        //        "title": "title",
        //        "content": "content"
        //        }
        //        """;
        ResultActions resultActions = mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));



        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.content").value(request.getContent()));

    }

    @Test
    public void findAllArticles() throws Exception {
        // 주어진 제목과 내용으로 새로운 Article 객체를 만든 후 저장
        Article savedArticle = Article.builder()
                .title("저장하려는 제목")
                .content("저장하려는 내용")
                .build();
        blogRepository.save(savedArticle);

        // /api/articles 엔드포인트에 GET 요청을 보내고 결과 받기
        ResultActions resultActions = mockMvc.perform(get("/api/articles"));

        // 응답 상태가 OK인지 확인
        resultActions.andExpect(status().isOk())

                // 응답이 배열로 온다면 첫 번째 항목에 대해 title을 검증
                .andExpect(jsonPath("$[0].title").value(savedArticle.getTitle()))

                // 응답이 배열로 온다면 첫 번째 항목에 대해 content를 검증
                .andExpect(jsonPath("$[0].content").value(savedArticle.getContent()));
    }


    @Test
    public void findArticleById()throws Exception{

        Article article = blogRepository.save(new Article("제목123", "내용123"));

        Long id = article.getId();

        ResultActions resultActions = mockMvc.perform(get("/api/articles/{id}", id));


        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(article.getTitle()))
                .andExpect(jsonPath("$.content").value(article.getContent()));
    }

    @Test
    public void deleteArticle()throws Exception{
        Article article = blogRepository.save(new Article("제목123", "내용123"));
        Long id = article.getId();

        ResultActions resultActions = mockMvc.perform(delete("/api/articles/{id}", id));

        resultActions.andExpect(status().isOk());

        List<Article> articles = blogRepository.findAll();
        assertTrue(articles.isEmpty());


    }
    @Test
    public void deleteAllArticles() throws Exception {
        // 여러 개의 Article 저장
        blogRepository.save(new Article("제목1", "내용1"));
        blogRepository.save(new Article("제목2", "내용2"));
        blogRepository.save(new Article("제목3", "내용3"));

        // MockMVC를 사용하여 전체 삭제 요청 수행
        ResultActions resultActions = mockMvc.perform(delete("/api/articles"));

        // HTTP 상태 코드 확인 (200 OK)
        resultActions.andExpect(status().isOk());

        // 모든 Article 검색하여 비어 있는지 확인
        List<Article> articles = blogRepository.findAll();
        assertTrue(articles.isEmpty(), "empty articles");
    }

    @DisplayName("블로그 수정 성공")
    @Test
    public void updateArticle() throws Exception {
        // given
        Article saved = blogRepository.save(new Article("타이틀", "컨텐츠"));
        Long id = saved.getId();
        UpdateArticleRequest request = new UpdateArticleRequest("modifiedTitle", "modifiedContent");


        String requestBody = objectMapper.writeValueAsString(request);
        // when
        ResultActions resultActions = mockMvc.perform(put("/api/articles/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));


        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.content").value(request.getContent()));

        Article article = blogRepository.findById(id).orElseThrow();
        assertThat(article.getTitle()).isEqualTo(request.getTitle());
        assertThat(article.getContent()).isEqualTo(request.getContent());
    }

    // todo Exception 발생시 400 status Code 검증하는 테스트 코드
    @Test
    public void updateArticleFailed()throws Exception{

        Long noExistsId = 1000L;
        UpdateArticleRequest request = new UpdateArticleRequest("modifiedTitle", "modifiedContent");
        String requestBody = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(put("/api/articles/{id}", noExistsId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));


        resultActions.andExpect(status().isBadRequest());
    }
}