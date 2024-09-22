package ru.practicum.ewm.main.controllers;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.dto.category.CategoryDto;
import ru.practicum.ewm.main.service.categories.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAll(
            @Min(0) @RequestParam(defaultValue = "0") Integer from,
            @Min(0) @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get запрос на получение всех категорий");
        return categoryService.getAll(from, size);
    }

    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getById(@PathVariable @Min(0) Long catId) {
        log.info("Get запрос на получение категории");
        return categoryService.getById(catId);
    }
}
