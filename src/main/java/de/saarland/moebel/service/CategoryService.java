package de.saarland.moebel.service;

import de.saarland.moebel.exception.ResourceConflictException;
import de.saarland.moebel.model.Category;
import de.saarland.moebel.repository.CategoryRepository;

import de.saarland.moebel.repository.ProductCollectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    // --- ИЗМЕНЯЕМ productRepository НА collectionRepository ---
    private final ProductCollectionRepository collectionRepository;


    public CategoryService(CategoryRepository categoryRepository, ProductCollectionRepository collectionRepository) {
        this.categoryRepository = categoryRepository;
        this.collectionRepository = collectionRepository;
    }

    @Transactional
    public Category createCategory(Category category) {

        categoryRepository.findByNameDe(category.getNameDe()).ifPresent(c -> {
            throw new ResourceConflictException("Category with name '" + c.getNameDe() + "' already exists.");
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


        categoryRepository.findByNameDeAndIdNot(categoryDetails.getNameDe(), id).ifPresent(c -> {
            throw new ResourceConflictException("Category with name '" + c.getNameDe() + "' already exists.");
        });


        categoryRepository.findBySlugAndIdNot(categoryDetails.getSlug(), id).ifPresent(c -> {
            throw new ResourceConflictException("Category with slug '" + c.getSlug() + "' already exists.");
        });

        category.setNameDe(categoryDetails.getNameDe());
        category.setSlug(categoryDetails.getSlug());


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

        // --- ИЗМЕНЕНИЕ ЛОГИКИ ПРОВЕРКИ ---
        // Проверяем, не использует ли какая-либо КОЛЛЕКЦИЯ эту категорию
        if (collectionRepository.existsByCategoryId(id)) {
            throw new ResourceConflictException("Cannot delete category with id '" + id + "' because it is used by collections.");
        }
        // --- КОНЕЦ ИЗМЕНЕНИЙ ---

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