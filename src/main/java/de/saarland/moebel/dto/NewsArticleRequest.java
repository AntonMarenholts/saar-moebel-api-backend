package de.saarland.moebel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsArticleRequest {
    @NotBlank private String titleDe;
    @NotBlank private String contentDe;
    @NotBlank private String imageUrl;
    @NotBlank private String titleEn;
    @NotBlank private String contentEn;
    @NotBlank private String titleFr;
    @NotBlank private String contentFr;
    @NotBlank private String titleRu;
    @NotBlank private String contentRu;
    @NotBlank private String titleUk;
    @NotBlank private String contentUk;
}