package ru.practicum.ewm.main.service.categories;

import ru.practicum.ewm.main.model.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(Long l, CategoryDto categoryDto);

    void delete(Long catId);

    List<CategoryDto> getAll(Boolean pinned, Integer from, Integer size);

    CategoryDto getById(Long catId);
}
