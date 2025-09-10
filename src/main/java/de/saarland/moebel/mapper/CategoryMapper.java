package de.saarland.moebel.mapper;

import de.saarland.moebel.dto.CategoryDto;
import de.saarland.moebel.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category) {
        if (category == null) return null;

        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setSlug(category.getSlug());
        dto.setImageUrl(category.getImageUrl());

        // Маппинг всех названий
        dto.setNameDe(category.getNameDe());
        dto.setNameEn(category.getNameEn());
        dto.setNameFr(category.getNameFr());
        dto.setNameRu(category.getNameRu());
        dto.setNameUk(category.getNameUk());

        return dto;
    }

    public Category toEntity(CategoryDto dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setId(dto.getId());
        category.setSlug(dto.getSlug());
        category.setImageUrl(dto.getImageUrl());

        // Маппинг всех названий
        category.setNameDe(dto.getNameDe());
        category.setNameEn(dto.getNameEn());
        category.setNameFr(dto.getNameFr());
        category.setNameRu(dto.getNameRu());
        category.setNameUk(dto.getNameUk());

        return category;
    }
}