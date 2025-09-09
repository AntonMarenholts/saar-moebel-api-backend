package de.saarland.moebel.repository;

import de.saarland.moebel.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    List<NewsArticle> findTop6ByOrderByCreatedAtDesc();
}