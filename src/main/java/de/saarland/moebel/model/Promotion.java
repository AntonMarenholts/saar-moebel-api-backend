package de.saarland.moebel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotions")
@Getter
@Setter
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String nameDe;
    private String nameEn;
    private String nameFr;
    private String nameRu;
    private String nameUk;

    @Column(columnDefinition = "TEXT")
    private String descriptionDe;
    @Column(columnDefinition = "TEXT")
    private String descriptionEn;
    @Column(columnDefinition = "TEXT")
    private String descriptionFr;
    @Column(columnDefinition = "TEXT")
    private String descriptionRu;
    @Column(columnDefinition = "TEXT")
    private String descriptionUk;

    @Column(nullable = false)
    private BigDecimal price;


    @Column
    private BigDecimal oldPrice;

    private String size;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}