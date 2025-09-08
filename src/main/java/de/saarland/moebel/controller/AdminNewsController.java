package de.saarland.moebel.controller;

import de.saarland.moebel.model.NewsArticle;
import de.saarland.moebel.repository.NewsArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;


    public AdminNewsController(NewsArticleRepository newsRepository, ModelMapper modelMapper) {
        this.newsRepository = newsRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<NewsArticle> getAllNews() {
        return newsRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<NewsArticle> createNews(@RequestBody NewsArticle newsArticle) {
        NewsArticle savedArticle = newsRepository.save(newsArticle);
        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsArticle> updateNews(@PathVariable Long id, @RequestBody NewsArticle newsDetails) {
        NewsArticle article = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News article not found with id: " + id));


        modelMapper.map(newsDetails, article);

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