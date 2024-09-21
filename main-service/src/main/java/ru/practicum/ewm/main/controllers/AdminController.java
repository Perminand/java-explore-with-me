package ru.practicum.ewm.main.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.dto.EventParamsLongDto;
import ru.practicum.ewm.main.dto.category.CategoryDto;
import ru.practicum.ewm.main.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.dto.compilation.UpdateCompilationRequestDto;
import ru.practicum.ewm.main.dto.event.EventFullDto;
import ru.practicum.ewm.main.dto.request.UpdateEventAdminRequestDto;
import ru.practicum.ewm.main.model.users.dto.UserDto;
import ru.practicum.ewm.main.service.categories.CategoryService;
import ru.practicum.ewm.main.service.compilations.CompilationService;
import ru.practicum.ewm.main.service.event.EventService;
import ru.practicum.ewm.main.service.users.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class AdminController {
    private final CategoryService categoryService;
    private final CompilationService compilationService;
    private final EventService eventService;
    private final UserService userService;

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
        categoryService.deleteById(catId);
    }

    @PostMapping("/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto compilationDto) {
        log.info("POST запрос на сохранение подборки");
        return compilationService.create(compilationDto);
    }

    @PatchMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@PathVariable @Min(0) Long compId, @Valid @RequestBody UpdateCompilationRequestDto compilationDto) {
        log.info("Patch запрос на обновление подборки");
        return compilationService.update(compId, compilationDto);
    }

    @DeleteMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable @Min(0) Long compId) {
        log.info("Delete запрос на удаление подборки");
        compilationService.deleteCompilation(compId);
    }

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEvents(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Get запрос на получение списка событий");
        return eventService.getEventsFull(new EventParamsLongDto(users, states, categories, rangeStart, rangeEnd, from, size));
    }

    @PatchMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(@Min(0) @PathVariable Long eventId, @RequestBody @Valid UpdateEventAdminRequestDto upupdateRequestDto) {
        log.info("Patch запрос на изменение события");
        return eventService.update(eventId, upupdateRequestDto);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        log.info("POST запрос на сохранение пользователя");
        return userService.create(userDto);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> getUsers(
            @RequestParam(name = "ids", required = false) Set<Long> listIds,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(0) Integer size) {
        log.info("Get Запрос на получение пользователей");
        return userService.getAll(listIds, from, size);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @NotNull @Min(0) Long userId) {
        log.info("Delete запрос на удаление пользователя");
        userService.delete(userId);
    }
}
