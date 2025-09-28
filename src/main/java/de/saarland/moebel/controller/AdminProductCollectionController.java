package de.saarland.moebel.controller;

import de.saarland.moebel.dto.ProductCollectionRequest;
import de.saarland.moebel.dto.ProductElementRequest;
import de.saarland.moebel.exception.ResourceConflictException;
import de.saarland.moebel.model.Category;
import de.saarland.moebel.model.Product;
import de.saarland.moebel.model.ProductCollection;
import de.saarland.moebel.repository.CategoryRepository;
import de.saarland.moebel.repository.ProductCollectionRepository;
import de.saarland.moebel.repository.ProductRepository;
import de.saarland.moebel.service.TranslationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import java.util.List;
import de.saarland.moebel.dto.ProductCollectionUpdateRequest;

@RestController
@RequestMapping("/api/admin/collections")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductCollectionController {

    private final ProductCollectionRepository collectionRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TranslationService translationService;

    public AdminProductCollectionController(ProductCollectionRepository collectionRepository, ProductRepository productRepository, CategoryRepository categoryRepository, TranslationService translationService) {
        this.collectionRepository = collectionRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.translationService = translationService;
    }

    @PostMapping
    public ResponseEntity<ProductCollection> createCollection(@Valid @RequestBody ProductCollectionRequest request) {
        collectionRepository.findBySlug(request.getSlug()).ifPresent(c -> {
            throw new ResourceConflictException("Collection with slug '" + request.getSlug() + "' already exists.");
        });

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        ProductCollection collection = new ProductCollection();
        collection.setNameDe(request.getNameDe());
        collection.setSlug(request.getSlug());
        collection.setDescriptionDe(request.getDescriptionDe());
        collection.setImageUrl(request.getImageUrl());
        collection.setCategory(category);

        collection.setNameEn(translationService.translate(request.getNameDe(), "EN-US"));
        collection.setDescriptionEn(translationService.translate(request.getDescriptionDe(), "EN-US"));
        collection.setNameFr(translationService.translate(request.getNameDe(), "FR"));
        collection.setDescriptionFr(translationService.translate(request.getDescriptionDe(), "FR"));
        collection.setNameRu(translationService.translate(request.getNameDe(), "RU"));
        collection.setDescriptionRu(translationService.translate(request.getDescriptionDe(), "RU"));
        collection.setNameUk(translationService.translate(request.getNameDe(), "UK"));
        collection.setDescriptionUk(translationService.translate(request.getDescriptionDe(), "UK"));

        ProductCollection savedCollection = collectionRepository.save(collection);
        return new ResponseEntity<>(savedCollection, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCollection> updateCollection(@PathVariable Long id, @Valid @RequestBody ProductCollectionUpdateRequest request) {
        ProductCollection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Collection not found with id: " + id));

        // Проверка на уникальность slug, если он изменился
        collectionRepository.findBySlug(request.getSlug()).ifPresent(existingCollection -> {
            if (!existingCollection.getId().equals(id)) {
                throw new ResourceConflictException("Collection with slug '" + request.getSlug() + "' already exists.");
            }
        });

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        collection.setNameDe(request.getNameDe());
        collection.setSlug(request.getSlug());
        collection.setDescriptionDe(request.getDescriptionDe());
        collection.setCategory(category);

        // Обновляем URL изображения, только если он был предоставлен
        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            collection.setImageUrl(request.getImageUrl());
        }

        // Повторный перевод для обновления всех полей
        collection.setNameEn(translationService.translate(request.getNameDe(), "EN-US"));
        collection.setDescriptionEn(translationService.translate(request.getDescriptionDe(), "EN-US"));
        collection.setNameFr(translationService.translate(request.getNameDe(), "FR"));
        collection.setDescriptionFr(translationService.translate(request.getDescriptionDe(), "FR"));
        collection.setNameRu(translationService.translate(request.getNameDe(), "RU"));
        collection.setDescriptionRu(translationService.translate(request.getDescriptionDe(), "RU"));
        collection.setNameUk(translationService.translate(request.getNameDe(), "UK"));
        collection.setDescriptionUk(translationService.translate(request.getDescriptionDe(), "UK"));

        ProductCollection updatedCollection = collectionRepository.save(collection);
        return ResponseEntity.ok(updatedCollection);
    }


    @PostMapping("/elements")
    public ResponseEntity<Product> addProductToCollection(@Valid @RequestBody ProductElementRequest request) {
        ProductCollection collection = collectionRepository.findById(request.getCollectionId())
                .orElseThrow(() -> new EntityNotFoundException("Collection not found"));

        Product product = new Product();

        // --- ИЗМЕНЕНИЕ ЗДЕСЬ ---
        // Заполняем оба поля из одного источника
        product.setName(request.getNameDe());
        product.setNameDe(request.getNameDe());

        product.setDescriptionDe(request.getDescriptionDe());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setCollection(collection);

        product.setNameEn(translationService.translate(request.getNameDe(), "EN-US"));
        product.setDescriptionEn(translationService.translate(request.getDescriptionDe(), "EN-US"));
        product.setNameFr(translationService.translate(request.getNameDe(), "FR"));
        product.setDescriptionFr(translationService.translate(request.getDescriptionDe(), "FR"));
        product.setNameRu(translationService.translate(request.getNameDe(), "RU"));
        product.setDescriptionRu(translationService.translate(request.getDescriptionDe(), "RU"));
        product.setNameUk(translationService.translate(request.getNameDe(), "UK"));
        product.setDescriptionUk(translationService.translate(request.getDescriptionDe(), "UK"));

        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductCollection>> getAllCollections() {
        List<ProductCollection> collections = collectionRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return ResponseEntity.ok(collections);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        if (!collectionRepository.existsById(id)) {
            throw new EntityNotFoundException("Collection not found with id: " + id);
        }
        collectionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}