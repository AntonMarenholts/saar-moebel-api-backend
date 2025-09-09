package de.saarland.moebel.repository;

import de.saarland.moebel.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByCategorySlug(String categorySlug);

    boolean existsByCategoryId(Long categoryId);

    Page<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);
}