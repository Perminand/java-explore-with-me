package ru.practicum.ewm.main.service.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.common.Utilities;
import ru.practicum.ewm.main.dto.category.CategoryDto;
import ru.practicum.ewm.main.exceptions.errors.ConflictException;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.mappers.CategoryMapper;
import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.repository.CategoryRepository;
import ru.practicum.ewm.main.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryRepository.save(CategoryMapper.toEntity(categoryDto));
        return CategoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(Long l, CategoryDto categoryDto) {
        Category category = getCategoryById(l);
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return CategoryMapper.toDto(category);

    }

    @Override
    public void deleteById(Long catId) {
        Category category = getCategoryById(catId);
        Optional<Event> eventOptional = eventRepository.findByCategoryId(catId);
        if (eventOptional.isPresent()) {
            throw new ConflictException("С категорией связано событие");
        }
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        return categoryRepository.findAll(Utilities.getPageableSortAscId(from, size)).stream()
                .map(CategoryMapper::toDto).toList();

    }

    @Override
    public CategoryDto getById(Long catId) {
        Category category = getCategoryById(catId);
        return CategoryMapper.toDto(category);
    }

    private Category getCategoryById(Long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> {
            log.error("Попытка создание события для несуществующей категории");
            throw new EntityNotFoundException("Нет категории с ид: " + catId);
        });
    }

}
