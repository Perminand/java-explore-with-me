package ru.practicum.ewm.main.mappers;

import ru.practicum.ewm.main.dto.category.CategoryDto;
import ru.practicum.ewm.main.model.category.Category;

public class CategoryMapper {
    public static Category toEntity(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
