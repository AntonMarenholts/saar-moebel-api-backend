package de.saarland.moebel.controller;

import de.saarland.moebel.model.Product;
import de.saarland.moebel.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @GetMapping("/latest")
    public ResponseEntity<Page<Product>> getLatestProducts(@PageableDefault(size = 6) Pageable pageable) {
        // Эта логика может потребовать пересмотра,
        // так как теперь у продуктов нет прямого createdAt.
        // Пока оставим так для совместимости.
        Page<Product> products = productRepository.findAll(pageable);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}