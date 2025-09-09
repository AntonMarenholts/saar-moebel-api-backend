package de.saarland.moebel.controller;

import de.saarland.moebel.dto.NewsArticleRequest;
import de.saarland.moebel.model.NewsArticle;
import de.saarland.moebel.repository.NewsArticleRepository;
import de.saarland.moebel.service.TranslationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/news")
@PreAuthorize("hasRole('ADMIN')")
public class AdminNewsController {

    private final NewsArticleRepository newsRepository;
    private final TranslationService translationService;


    public AdminNewsController(NewsArticleRepository newsRepository, TranslationService translationService) {
        this.newsRepository = newsRepository;
        this.translationService = translationService;
    }

    @GetMapping
    public List<NewsArticle> getAllNews() {
        return newsRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<NewsArticle> createNews(@RequestBody NewsArticleRequest request) {
        NewsArticle article = new NewsArticle();
        article.setTitleDe(request.getTitleDe());
        article.setContentDe(request.getContentDe());
        article.setImageUrl(request.getImageUrl());

        // Автоматический перевод
        article.setTitleEn(translationService.translate(request.getTitleDe(), "EN-US"));
        article.setContentEn(translationService.translate(request.getContentDe(), "EN-US"));

        article.setTitleFr(translationService.translate(request.getTitleDe(), "FR"));
        article.setContentFr(translationService.translate(request.getContentDe(), "FR"));

        article.setTitleRu(translationService.translate(request.getTitleDe(), "RU"));
        article.setContentRu(translationService.translate(request.getContentDe(), "RU"));

        article.setTitleUk(translationService.translate(request.getTitleDe(), "UK"));
        article.setContentUk(translationService.translate(request.getContentDe(), "UK"));


        NewsArticle savedArticle = newsRepository.save(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsArticle> updateNews(@PathVariable Long id, @RequestBody NewsArticleRequest request) {
        NewsArticle article = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News article not found with id: " + id));

        article.setTitleDe(request.getTitleDe());
        article.setContentDe(request.getContentDe());
        article.setImageUrl(request.getImageUrl());

        // Автоматический перевод при обновлении
        article.setTitleEn(translationService.translate(request.getTitleDe(), "EN-US"));
        article.setContentEn(translationService.translate(request.getContentDe(), "EN-US"));

        article.setTitleFr(translationService.translate(request.getTitleDe(), "FR"));
        article.setContentFr(translationService.translate(request.getContentDe(), "FR"));

        article.setTitleRu(translationService.translate(request.getTitleDe(), "RU"));
        article.setContentRu(translationService.translate(request.getContentDe(), "RU"));

        article.setTitleUk(translationService.translate(request.getTitleDe(), "UK"));
        article.setContentUk(translationService.translate(request.getContentDe(), "UK"));

        NewsArticle updatedArticle = newsRepository.save(article);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        if (!newsRepository.existsById(id)) {
            throw new EntityNotFoundException("News article not found with id: " + id);
        }
        newsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}