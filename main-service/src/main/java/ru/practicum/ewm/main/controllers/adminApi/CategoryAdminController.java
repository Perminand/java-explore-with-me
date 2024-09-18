package ru.practicum.ewm.main.controllers.adminApi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.model.category.dto.CategoryDto;
import ru.practicum.ewm.main.service.categories.CategoryService;

@Slf4j
@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class CategoryAdminController {
    private final CategoryService categoryService;

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("POST запрос на сохранение категории");
        return categoryService.create(categoryDto);
    }

    @PatchMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable @Min(0) Long catId, @Valid @RequestBody CategoryDto categoryDto) {
        log.info("Patch запрос на обновление категории");
        return categoryService.update(catId, categoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable @Min(0) Long catId) {
        log.info("Delete запрос на удаление категории");
        categoryService.delete(catId);
    }
}
