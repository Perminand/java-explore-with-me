package ru.practicum.ewm.main.mappers;

import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.model.category.dto.CategoryDto;

public class CategoryMappers {
    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
