package de.saarland.moebel.repository;

import de.saarland.moebel.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
}