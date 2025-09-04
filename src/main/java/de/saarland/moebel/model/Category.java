package de.saarland.moebel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor // Lombok annotation to create a no-argument constructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    public Category(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
