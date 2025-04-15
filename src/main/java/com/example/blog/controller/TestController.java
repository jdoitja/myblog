package com.example.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class TestController {

//    @GetMapping("/test")
//    public ResponseEntity<Map<String, Object>> testFilter(@RequestParam String query){
//        log.info("Test filter query: " + query);
//        Map<String, Object> hashMap = new HashMap<>();
//        hashMap.put("query", query);
//
//        return ResponseEntity.ok(hashMap);
//    }

    @GetMapping("/test")
    public String testFilterInterceptor(@RequestParam String query, ModelAndView modelAndView) {
        log.info("testController testFilter()");
        modelAndView.addObject("query", query);
        return "test";
    }
}
