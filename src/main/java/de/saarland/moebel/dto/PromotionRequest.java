package de.saarland.moebel.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PromotionRequest {

    @NotBlank
    private String nameDe;

    private String descriptionDe;

    @NotNull
    @Positive
    private BigDecimal price;

    private String size;

    @NotBlank
    private String imageUrl;

    @NotNull
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    private LocalDate endDate;
}