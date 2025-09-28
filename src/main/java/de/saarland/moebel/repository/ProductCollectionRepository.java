package de.saarland.moebel.repository;

import de.saarland.moebel.model.ProductCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // Убедитесь, что этот импорт есть

@Repository
public interface ProductCollectionRepository extends JpaRepository<ProductCollection, Long> {
    List<ProductCollection> findByCategorySlug(String categorySlug);

    // --- ДОБАВЬТЕ ЭТОТ МЕТОД ---
    boolean existsByCategoryId(Long categoryId);

    // Этот метод нам тоже пригодится в будущем
    Optional<ProductCollection> findBySlug(String slug);
}