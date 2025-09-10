package de.saarland.moebel.repository;

import de.saarland.moebel.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findBySlug(String slug);

    // --- ИЗМЕНЕНИЯ ЗДЕСЬ ---
    Optional<Category> findByNameDe(String nameDe);

    Optional<Category> findByNameDeAndIdNot(String nameDe, Long id);
    // --- КОНЕЦ ИЗМЕНЕНИЙ ---

    Optional<Category> findBySlugAndIdNot(String slug, Long id);
}