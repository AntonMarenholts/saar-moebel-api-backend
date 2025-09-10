package de.saarland.moebel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TranslationResponse {
    private String titleEn;
    private String contentEn;
    private String titleFr;
    private String contentFr;
    private String titleRu;
    private String contentRu;
    private String titleUk;
    private String contentUk;
}