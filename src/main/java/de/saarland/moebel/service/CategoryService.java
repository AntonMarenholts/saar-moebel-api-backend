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

        categoryRepository.findByName(category.getName()).ifPresent(c -> {
            throw new ResourceConflictException("Category with name '" + c.getName() + "' already exists.");
        });
        categoryRepository.findBySlug(category.getSlug()).ifPresent(c -> {
            throw new ResourceConflictException("Category with slug '" + c.getSlug() + "' already exists.");
        });

        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));


        categoryRepository.findByNameAndIdNot(categoryDetails.getName(), id).ifPresent(c -> {
            throw new ResourceConflictException("Category with name '" + c.getName() + "' already exists.");
        });

        categoryRepository.findBySlugAndIdNot(categoryDetails.getSlug(), id).ifPresent(c -> {
            throw new ResourceConflictException("Category with slug '" + c.getSlug() + "' already exists.");
        });

        category.setName(categoryDetails.getName());
        category.setSlug(categoryDetails.getSlug());


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