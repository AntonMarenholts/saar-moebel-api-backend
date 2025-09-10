package de.saarland.moebel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Старое поле name переименовываем в nameDe
    @Column(nullable = false, unique = true)
    private String nameDe;

    @Column(nullable = false, unique = true)
    private String slug;

    private String imageUrl;

    // Новые поля для переводов
    private String nameEn;
    private String nameFr;
    private String nameRu;
    private String nameUk;

    public Category(String nameDe, String slug) {
        this.nameDe = nameDe;
        this.slug = slug;
    }
}