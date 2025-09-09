package de.saarland.moebel.controller;

import de.saarland.moebel.dto.ProductRequest;
import de.saarland.moebel.model.Category;
import de.saarland.moebel.model.Product;
import de.saarland.moebel.repository.CategoryRepository;
import de.saarland.moebel.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/products/latest")
    public ResponseEntity<Page<Product>> getLatestProducts(@PageableDefault(size = 6) Pageable pageable) {
        Page<Product> products = productRepository.findAllByOrderByCreatedAtDesc(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }


    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }


    @PostMapping("/products")
    @PreAuthorize("hasRole('ADMIN')") // <-- Только для админов
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest productRequest) {

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productRequest.getCategoryId()));


        Product newProduct = new Product(
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.getImageUrl(),
                category
        );


        Product savedProduct = productRepository.save(newProduct);


        return ResponseEntity.created(URI.create("/api/products/" + savedProduct.getId())).body(savedProduct);
    }
    @GetMapping("/products/category/{categorySlug}")
    public ResponseEntity<List<Product>> getProductsByCategorySlug(@PathVariable String categorySlug) {
        List<Product> products = productRepository.findByCategorySlug(categorySlug);
        if (products.isEmpty()) {

            return ResponseEntity.ok(products);
        }
        return ResponseEntity.ok(products);
    }

}