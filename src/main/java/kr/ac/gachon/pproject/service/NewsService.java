package kr.ac.gachon.pproject.service;

import kr.ac.gachon.pproject.temp.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.net.URI;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {
    //"all" ? "" : `&category=${category}`
    private final String url = "https://newsapi.org/v2/top-headlines?country=kr";
    public String getNews(String category) {
        String apiUrl;
        String apiKey = "&apiKey=" + Constant.NEWS_API_KEY;
        if (category == "all") {
            apiUrl = url + apiKey;
        } else {
            apiUrl = url + String.format("&query=%s", category) + apiKey;
        }

        URI uri = URI.create(apiUrl);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> res = restTemplate.getForEntity(uri, String.class);

        return res.getBody();
    }
}
