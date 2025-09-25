package de.saarland.moebel.controller;

import de.saarland.moebel.model.Promotion;
import de.saarland.moebel.repository.PromotionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionRepository promotionRepository;

    public PromotionController(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }


    @GetMapping
    public ResponseEntity<Page<Promotion>> getActivePromotions(
            @PageableDefault(size = 12, sort = "endDate", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<Promotion> activePromotions = promotionRepository.findByEndDateAfter(LocalDate.now(), pageable);
        return ResponseEntity.ok(activePromotions);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Long id) {
        return promotionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}