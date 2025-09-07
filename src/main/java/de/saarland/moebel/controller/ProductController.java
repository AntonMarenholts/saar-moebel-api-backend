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

    // Эндпоинт для получения всех товаров
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    // Эндпоинт для получения всех категорий
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    // ++ НАЧАЛО ИЗМЕНЕНИЙ: Добавляем эндпоинт для создания товара ++
    @PostMapping("/products")
    @PreAuthorize("hasRole('ADMIN')") // <-- Только для админов
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        // Находим категорию по ID, иначе выбрасываем ошибку
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productRequest.getCategoryId()));

        // Создаем новый объект Product на основе запроса
        Product newProduct = new Product(
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.getImageUrl(),
                category
        );

        // Сохраняем товар в базу данных
        Product savedProduct = productRepository.save(newProduct);

        // Возвращаем ответ 201 Created с созданным товаром
        return ResponseEntity.created(URI.create("/api/products/" + savedProduct.getId())).body(savedProduct);
    }
    // -- КОНЕЦ ИЗМЕНЕНИЙ --
}