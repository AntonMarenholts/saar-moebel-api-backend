package de.saarland.moebel.repository;

import de.saarland.moebel.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Метод для поиска категории по ее "slug" (короткому имени для URL)
    Optional<Category> findBySlug(String slug);
}