package de.saarland.moebel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductElementRequest {

    @NotBlank
    private String nameDe;
    private String descriptionDe;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank
    private String imageUrl;


    @NotNull
    private Long collectionId;
}