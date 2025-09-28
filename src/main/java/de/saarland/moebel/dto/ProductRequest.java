package de.saarland.moebel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {


    @NotBlank(message = "Product name (DE) cannot be blank")
    private String nameDe;

    private String descriptionDe;

    // Поля для других языков (опционально, будут заполняться позже)
    private String nameEn;
    private String descriptionEn;
    private String nameFr;
    private String descriptionFr;
    private String nameRu;
    private String descriptionRu;
    private String nameUk;
    private String descriptionUk;


    private String collectionSlug;


    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private String imageUrl;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;
}