package de.saarland.moebel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Long id;

    @NotBlank(message = "Category name (DE) cannot be blank")
    private String nameDe;

    @NotBlank(message = "Category slug cannot be blank")
    private String slug;

    private String imageUrl;

    // Поля для других языков
    private String nameEn;
    private String nameFr;
    private String nameRu;
    private String nameUk;
}