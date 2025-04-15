package com.example.blog.filter;

//필터 등록
//- 빈으로 등록
//- 필터 체인 순서지정
//- url 패턴

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean//필터를 빈으로 등록
    public FilterRegistrationBean<Filter> firstFilter() {
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new FirstFilter());
        filter.setOrder(1);
        filter.addUrlPatterns("/test");//url 패턴 설정 test로 들어오면 필터
        return filter;
    }

    @Bean
    public FilterRegistrationBean<Filter> secondFilter() {
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new SecondFilter());
        filter.setOrder(2);
        filter.addUrlPatterns("/test");
        return filter;
    }
}
