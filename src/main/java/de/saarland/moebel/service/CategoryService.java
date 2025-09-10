package de.saarland.moebel.service;

import de.saarland.moebel.exception.ResourceConflictException;
import de.saarland.moebel.model.Category;
import de.saarland.moebel.repository.CategoryRepository;
import de.saarland.moebel.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Category createCategory(Category category) {
        // --- ИЗМЕНЕНИЯ ЗДЕСЬ ---
        categoryRepository.findByNameDe(category.getNameDe()).ifPresent(c -> {
            throw new ResourceConflictException("Category with name '" + c.getNameDe() + "' already exists.");
        });
        // --- КОНЕЦ ИЗМЕНЕНИЙ ---

        categoryRepository.findBySlug(category.getSlug()).ifPresent(c -> {
            throw new ResourceConflictException("Category with slug '" + c.getSlug() + "' already exists.");
        });

        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        // --- ИЗМЕНЕНИЯ ЗДЕСЬ ---
        categoryRepository.findByNameDeAndIdNot(categoryDetails.getNameDe(), id).ifPresent(c -> {
            throw new ResourceConflictException("Category with name '" + c.getNameDe() + "' already exists.");
        });
        // --- КОНЕЦ ИЗМЕНЕНИЙ ---

        categoryRepository.findBySlugAndIdNot(categoryDetails.getSlug(), id).ifPresent(c -> {
            throw new ResourceConflictException("Category with slug '" + c.getSlug() + "' already exists.");
        });

        category.setNameDe(categoryDetails.getNameDe());
        category.setSlug(categoryDetails.getSlug());

        // Обновляем также переводы, если они переданы
        category.setNameEn(categoryDetails.getNameEn());
        category.setNameFr(categoryDetails.getNameFr());
        category.setNameRu(categoryDetails.getNameRu());
        category.setNameUk(categoryDetails.getNameUk());

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }

        if (productRepository.existsByCategoryId(id)) {
            throw new ResourceConflictException("Cannot delete category with id '" + id + "' because it contains products.");
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