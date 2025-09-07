package de.saarland.moebel.service;

import de.saarland.moebel.model.Category;
import de.saarland.moebel.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category createCategory(Category category) {
        // Здесь можно добавить проверки, например, на уникальность имени или slug
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        category.setName(categoryDetails.getName());
        category.setSlug(categoryDetails.getSlug());

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        // Здесь можно добавить проверку, есть ли товары в этой категории, прежде чем удалять
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
    @Transactional
    public Category updateCategoryImage(Long id, String imageUrl) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        category.setImageUrl(imageUrl);
        return categoryRepository.save(category);
    }
}