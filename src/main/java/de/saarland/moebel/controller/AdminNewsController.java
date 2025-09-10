package de.saarland.moebel.controller;

import de.saarland.moebel.dto.NewsArticleRequest;
import de.saarland.moebel.dto.TranslationRequest;
import de.saarland.moebel.dto.TranslationResponse;
import de.saarland.moebel.model.NewsArticle;
import de.saarland.moebel.repository.NewsArticleRepository;
import de.saarland.moebel.service.TranslationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/translate")
    public ResponseEntity<TranslationResponse> translateNews(@RequestBody TranslationRequest request) {
        String titleEn = translationService.translate(request.getTitleDe(), "EN-US");
        String contentEn = translationService.translate(request.getContentDe(), "EN-US");
        String titleFr = translationService.translate(request.getTitleDe(), "FR");
        String contentFr = translationService.translate(request.getContentDe(), "FR");
        String titleRu = translationService.translate(request.getTitleDe(), "RU");
        String contentRu = translationService.translate(request.getContentDe(), "RU");
        String titleUk = translationService.translate(request.getTitleDe(), "UK");
        String contentUk = translationService.translate(request.getContentDe(), "UK");

        TranslationResponse response = new TranslationResponse(titleEn, contentEn, titleFr, contentFr, titleRu, contentRu, titleUk, contentUk);
        return ResponseEntity.ok(response);
    }

    // Метод для получения новостей с пагинацией
    @GetMapping("/all")
    public Page<NewsArticle> getAllNews(
            @PageableDefault(size = 6, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return newsRepository.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<NewsArticle> createNews(@RequestBody NewsArticleRequest request) {
        NewsArticle article = new NewsArticle();
        article.setTitleDe(request.getTitleDe());
        article.setContentDe(request.getContentDe());
        article.setImageUrl(request.getImageUrl());
        article.setTitleEn(request.getTitleEn());
        article.setContentEn(request.getContentEn());
        article.setTitleFr(request.getTitleFr());
        article.setContentFr(request.getContentFr());
        article.setTitleRu(request.getTitleRu());
        article.setContentRu(request.getContentRu());
        article.setTitleUk(request.getTitleUk());
        article.setContentUk(request.getContentUk());

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
        article.setTitleEn(request.getTitleEn());
        article.setContentEn(request.getContentEn());
        article.setTitleFr(request.getTitleFr());
        article.setContentFr(request.getContentFr());
        article.setTitleRu(request.getTitleRu());
        article.setContentRu(request.getContentRu());
        article.setTitleUk(request.getTitleUk());
        article.setContentUk(request.getContentUk());

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