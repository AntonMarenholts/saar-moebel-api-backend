package de.saarland.moebel.repository;

import de.saarland.moebel.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Метод для поиска всех товаров в определенной категории по ее ID
    List<Product> findByCategoryId(Long categoryId);

    // Метод для поиска всех товаров в определенной категории по ее "slug"
    List<Product> findByCategorySlug(String categorySlug);
}