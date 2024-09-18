package ru.practicum.ewm.main.service.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.exceptions.errors.ConflictException;
import ru.practicum.ewm.main.mappers.CategoryMappers;
import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.model.category.dto.CategoryDto;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.repository.CategoryRepository;
import ru.practicum.ewm.main.repository.EventRepository;
import ru.practicum.ewm.main.validate.Validate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final Validate validate;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryRepository.save(CategoryMappers.toCategory(categoryDto));
        return CategoryMappers.toCategoryDto(category);
    }

    @Override
    public CategoryDto update(Long l, CategoryDto categoryDto) {
        Category category = validate.getCategoryById(l);
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return CategoryMappers.toCategoryDto(category);

    }

    @Override
    public void delete(Long catId) {
        Category category = validate.getCategoryById(catId);
        Optional<Event> eventOptional = eventRepository.findByCategoryId(catId);
        if (eventOptional.isPresent()) {
            throw new ConflictException("С категорией связано событие");
        }
        categoryRepository.delete(category);
    }


    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        int startPage = from > 0 ? from/size : 0;
        Pageable pageable = PageRequest.of(startPage, size, sortById);
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMappers::toCategoryDto).toList();

    }

    @Override
    public CategoryDto getById(Long catId) {
        Category category = validate.getCategoryById(catId);
        return CategoryMappers.toCategoryDto(category);
    }

}
