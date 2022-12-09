package kr.ac.gachon.pproject.controller;

import kr.ac.gachon.pproject.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    private final String[] CATEGORY_LIST = {"all", "business", "entertainment", "health", "science", "sports", "technology"};
    private final ArrayList<String> CATEGORYS = new ArrayList<>(Arrays.asList(CATEGORY_LIST));
    @GetMapping("/getNews")
    public ResponseEntity<Object> getNews(@RequestParam(value = "category", required = true) String category) {
        HashMap map = new HashMap<>();

        if (!CATEGORYS.contains(category)){
            map.put("status", 490);
            map.put("errorMessage", "category value is error");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        String res = newsService.getNews(category);
        map.put("result", res);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
