package de.saarland.moebel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCollectionRequest {

    @NotBlank
    private String slug;

    @NotBlank
    private String nameDe;

    private String descriptionDe;

    @NotBlank
    private String imageUrl;

    @NotNull
    private Long categoryId;
}