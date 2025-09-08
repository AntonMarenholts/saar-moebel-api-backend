package de.saarland.moebel.repository;

import de.saarland.moebel.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findBySlug(String slug);

    Optional<Category> findByName(String name);

    Optional<Category> findByNameAndIdNot(String name, Long id);

    Optional<Category> findBySlugAndIdNot(String slug, Long id);
}