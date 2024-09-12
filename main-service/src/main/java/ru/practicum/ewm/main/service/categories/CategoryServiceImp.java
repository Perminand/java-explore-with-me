package ru.practicum.ewm.main.service.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.mappers.CategoryMappers;
import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.model.category.dto.CategoryDto;
import ru.practicum.ewm.main.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository repository;

    @Override
    @Transactional
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = repository.save(CategoryMappers.toCategory(categoryDto));
        return CategoryMappers.toCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryDto update(Long l, CategoryDto categoryDto) {
        Category category = validateCategory(l);
        category.setName(categoryDto.getName());
        repository.save(category);
        return CategoryMappers.toCategoryDto(category);

    }

    @Override
    public void delete(Long catId) {
        validateCategory(catId);
        repository.deleteById(catId);
    }


    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        int startPage = from > 0 ? from/size : 0;
        Pageable pageable = PageRequest.of(startPage, size, sortById);
        return repository.findAll(pageable).stream()
                .map(CategoryMappers::toCategoryDto).toList();

    }

    @Override
    public CategoryDto getById(Long catId) {
        return CategoryMappers.toCategoryDto(repository.findById(catId).get());
    }

    private Category validateCategory(Long l) {
        return Optional.of(repository.findById(l))
                .orElseThrow(() -> {
                    log.error("Попытка операции над не существующей категории");
                    throw new EntityNotFoundException("Нет категории с ид: " + l);
                }).get();
    }
}
