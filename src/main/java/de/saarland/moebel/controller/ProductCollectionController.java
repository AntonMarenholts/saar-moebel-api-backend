package de.saarland.moebel.controller;

import de.saarland.moebel.model.ProductCollection;
import de.saarland.moebel.repository.ProductCollectionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/collections")
public class ProductCollectionController {

    private final ProductCollectionRepository collectionRepository;

    public ProductCollectionController(ProductCollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }


    @GetMapping("/category/{categorySlug}")
    public ResponseEntity<List<ProductCollection>> getCollectionsByCategory(@PathVariable String categorySlug) {
        return ResponseEntity.ok(collectionRepository.findByCategorySlug(categorySlug));
    }


    // --- ИЗМЕНЕНИЕ ЗДЕСЬ ---
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ProductCollection> getCollectionById(@PathVariable Long id) {
        Optional<ProductCollection> collectionOptional = collectionRepository.findById(id);
        if (collectionOptional.isPresent()) {
            ProductCollection collection = collectionOptional.get();
            // Принудительная загрузка ленивых коллекций
            collection.getProducts().size();
            return ResponseEntity.ok(collection);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/slug/{slug}")
    @Transactional(readOnly = true)
    public ResponseEntity<ProductCollection> getCollectionBySlug(@PathVariable String slug) {
        Optional<ProductCollection> collectionOptional = collectionRepository.findBySlug(slug);
        if (collectionOptional.isPresent()) {
            ProductCollection collection = collectionOptional.get();
            // Принудительная загрузка ленивой коллекции продуктов
            collection.getProducts().size();
            return ResponseEntity.ok(collection);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}