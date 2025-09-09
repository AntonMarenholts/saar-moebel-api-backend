package de.saarland.moebel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsArticleRequest {

    @NotBlank(message = "Title (DE) cannot be blank")
    private String titleDe;

    @NotBlank(message = "Content (DE) cannot be blank")
    private String contentDe;

    @NotBlank(message = "Image URL cannot be blank")
    private String imageUrl;
}