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
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setImageUrl(category.getImageUrl());
        return dto;
    }

    public Category toEntity(CategoryDto dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        category.setImageUrl(dto.getImageUrl());
        return category;
    }
}