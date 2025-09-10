package de.saarland.moebel.repository;

import de.saarland.moebel.model.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    // Поиск активных акций для публичной страницы
    Page<Promotion> findByEndDateAfter(LocalDate date, Pageable pageable);
}