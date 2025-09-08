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
    @Lob
    private String contentDe;

    private String titleEn;
    @Lob
    private String contentEn;

    private String titleFr;
    @Lob
    private String contentFr;

    private String titleRu;
    @Lob
    private String contentRu;

    private String titleUk;
    @Lob
    private String contentUk;

    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}