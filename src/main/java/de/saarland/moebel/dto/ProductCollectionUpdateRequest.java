package de.saarland.moebel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCollectionUpdateRequest {

    @NotBlank
    private String slug;

    @NotBlank
    private String nameDe;

    private String descriptionDe;

    @NotNull
    private Long categoryId;

    // imageUrl не является обязательным,
    // так как изображение может не меняться при каждом обновлении
    private String imageUrl;
}