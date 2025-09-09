package de.saarland.moebel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "news_articles")
@Getter
@Setter
@NoArgsConstructor
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titleDe;

    // V-- ЗАМЕНА НАЧАЛАСЬ ЗДЕСЬ --V
    @Column(columnDefinition = "TEXT")
    private String contentDe;

    private String titleEn;
    @Column(columnDefinition = "TEXT")
    private String contentEn;

    private String titleFr;
    @Column(columnDefinition = "TEXT")
    private String contentFr;

    private String titleRu;
    @Column(columnDefinition = "TEXT")
    private String contentRu;

    private String titleUk;
    @Column(columnDefinition = "TEXT")
    private String contentUk;
    // ^-- ЗАМЕНА ЗАКОНЧИЛАСЬ ЗДЕСЬ --^

    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}