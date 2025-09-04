package de.saarland.moebel.controller;

import de.saarland.moebel.model.Category;
import de.saarland.moebel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Этот эндпоинт остается публичным - все могут видеть категории
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Этот эндпоинт тоже публичный
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return ResponseEntity.ok(category);
    }

    // А вот этот эндпоинт теперь только для администратора
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    // И этот только для администратора
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    // И этот тоже
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
