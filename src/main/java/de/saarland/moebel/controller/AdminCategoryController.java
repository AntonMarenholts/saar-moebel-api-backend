package de.saarland.moebel.controller;

import de.saarland.moebel.dto.CategoryDto;
import de.saarland.moebel.mapper.CategoryMapper;
import de.saarland.moebel.model.Category;
import de.saarland.moebel.service.CategoryService;
import de.saarland.moebel.service.TranslationService; // Импортируем сервис перевода
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/categories")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final TranslationService translationService;

    public AdminCategoryController(CategoryService categoryService, CategoryMapper categoryMapper, TranslationService translationService) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
        this.translationService = translationService; // Инициализируем
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);

        // Автоматически переводим название при создании
        category.setNameEn(translationService.translate(category.getNameDe(), "EN-US"));
        category.setNameFr(translationService.translate(category.getNameDe(), "FR"));
        category.setNameRu(translationService.translate(category.getNameDe(), "RU"));
        category.setNameUk(translationService.translate(category.getNameDe(), "UK"));

        Category savedCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(categoryMapper.toDto(savedCategory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        Category categoryDetails = categoryMapper.toEntity(categoryDto);
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
        return ResponseEntity.ok(categoryMapper.toDto(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/image")
    public ResponseEntity<CategoryDto> updateCategoryImage(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String imageUrl = request.get("imageUrl");
        if (imageUrl == null || imageUrl.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Category updatedCategory = categoryService.updateCategoryImage(id, imageUrl);
        return ResponseEntity.ok(categoryMapper.toDto(updatedCategory));
    }
}