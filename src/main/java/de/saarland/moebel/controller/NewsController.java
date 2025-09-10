package de.saarland.moebel.controller;

import de.saarland.moebel.model.NewsArticle;
import de.saarland.moebel.repository.NewsArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsArticleRepository newsArticleRepository;

    public NewsController(NewsArticleRepository newsArticleRepository) {
        this.newsArticleRepository = newsArticleRepository;
    }

    @GetMapping("/latest")
    public ResponseEntity<Page<NewsArticle>> getLatestNews(
            @PageableDefault(size = 6, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<NewsArticle> latestNews = newsArticleRepository.findAll(pageable);
        return ResponseEntity.ok(latestNews);
    }
}