package ru.practicum.ewm.main.service.categories;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.model.dto.category.CategoryDto;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public CategoryDto update(Long l, CategoryDto categoryDto) {
        return null;
    }

    @Override
    public void delete(Long catId) {

    }

    @Override
    public List<CategoryDto> getAll(Boolean pinned, Integer from, Integer size) {
        return null;
    }

    @Override
    public CategoryDto getById(Long catId) {
        return null;
    }
}
