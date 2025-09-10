package de.saarland.moebel.controller;

import de.saarland.moebel.dto.PromotionRequest;
import de.saarland.moebel.model.Promotion;
import de.saarland.moebel.repository.PromotionRepository;
import de.saarland.moebel.service.TranslationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/promotions")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPromotionController {

    private final PromotionRepository promotionRepository;
    private final TranslationService translationService;

    public AdminPromotionController(PromotionRepository promotionRepository, TranslationService translationService) {
        this.promotionRepository = promotionRepository;
        this.translationService = translationService;
    }

    // Получить все акции (включая архивные) для админки
    @GetMapping
    public Page<Promotion> getAllPromotions(@PageableDefault(size = 8, sort = "endDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }

    // Создать новую акцию
    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@Valid @RequestBody PromotionRequest request) {
        Promotion promotion = new Promotion();
        mapRequestToPromotion(request, promotion);

        // Переводим текстовые поля
        promotion.setNameEn(translationService.translate(request.getNameDe(), "EN-US"));
        promotion.setDescriptionEn(translationService.translate(request.getDescriptionDe(), "EN-US"));
        promotion.setNameFr(translationService.translate(request.getNameDe(), "FR"));
        promotion.setDescriptionFr(translationService.translate(request.getDescriptionDe(), "FR"));
        promotion.setNameRu(translationService.translate(request.getNameDe(), "RU"));
        promotion.setDescriptionRu(translationService.translate(request.getDescriptionDe(), "RU"));
        promotion.setNameUk(translationService.translate(request.getNameDe(), "UK"));
        promotion.setDescriptionUk(translationService.translate(request.getDescriptionDe(), "UK"));

        Promotion savedPromotion = promotionRepository.save(promotion);
        return new ResponseEntity<>(savedPromotion, HttpStatus.CREATED);
    }

    // Обновить акцию
    @PutMapping("/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable Long id, @Valid @RequestBody PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promotion not found with id: " + id));

        mapRequestToPromotion(request, promotion);

        // Также переводим поля при обновлении
        promotion.setNameEn(translationService.translate(request.getNameDe(), "EN-US"));
        promotion.setDescriptionEn(translationService.translate(request.getDescriptionDe(), "EN-US"));
        // ... и так далее для других языков
        promotion.setNameFr(translationService.translate(request.getNameDe(), "FR"));
        promotion.setDescriptionFr(translationService.translate(request.getDescriptionDe(), "FR"));
        promotion.setNameRu(translationService.translate(request.getNameDe(), "RU"));
        promotion.setDescriptionRu(translationService.translate(request.getDescriptionDe(), "RU"));
        promotion.setNameUk(translationService.translate(request.getNameDe(), "UK"));
        promotion.setDescriptionUk(translationService.translate(request.getDescriptionDe(), "UK"));

        Promotion updatedPromotion = promotionRepository.save(promotion);
        return ResponseEntity.ok(updatedPromotion);
    }

    // Удалить акцию
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        if (!promotionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        promotionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Вспомогательный метод для маппинга
    private void mapRequestToPromotion(PromotionRequest request, Promotion promotion) {
        promotion.setNameDe(request.getNameDe());
        promotion.setDescriptionDe(request.getDescriptionDe());
        promotion.setPrice(request.getPrice());
        promotion.setSize(request.getSize());
        promotion.setImageUrl(request.getImageUrl());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());
    }
}