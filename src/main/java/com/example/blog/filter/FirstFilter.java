package com.example.blog.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

//정보 로그로 남기기
@Slf4j
public class FirstFilter implements Filter {

    //private final static Logger log = LoggerFactory.getLogger(FirstFilter.class);
    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig)  throws ServletException {
        log.info("FirstFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();

        log.info("requestURI: " + requestURI);

        filterChain.doFilter(servletRequest, servletResponse);


    }

    @Override
    public void destroy() {
        log.info("FirstFilter destroy");
    }
}
