package de.saarland.moebel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Long id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;

    @NotBlank(message = "Category slug cannot be blank")
    private String slug;

    // ++ ИЗМЕНЕНИЕ: Добавляем поле ++
    private String imageUrl;
}