package de.saarland.moebel.controller;

import de.saarland.moebel.model.NewsArticle;
import de.saarland.moebel.repository.NewsArticleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsArticleRepository newsArticleRepository;

    public NewsController(NewsArticleRepository newsArticleRepository) {
        this.newsArticleRepository = newsArticleRepository;
    }

    @GetMapping("/latest")
    public ResponseEntity<List<NewsArticle>> getLatestNews() {
        List<NewsArticle> latestNews = newsArticleRepository.findTop6ByOrderByCreatedAtDesc();
        return ResponseEntity.ok(latestNews);
    }
}