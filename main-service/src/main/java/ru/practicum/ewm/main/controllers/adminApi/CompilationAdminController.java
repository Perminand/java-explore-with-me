package ru.practicum.ewm.main.controllers.adminApi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.model.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main.model.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.ewm.main.service.compilations.CompilationService;

@Slf4j
@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class CompilationAdminController {
    private final CompilationService compilationService;

    @PostMapping("/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto compilationDto) {
        log.info("POST запрос на сохранение подборки");
        return compilationService.create(compilationDto);
    }

    @PatchMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@Min(0) Long compId, @Valid @PathVariable UpdateCompilationRequestDto compilationDto) {
        log.info("Patch запрос на обновление подборки");
        return compilationService.update(compId, compilationDto);
    }

    @DeleteMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@Min(0) Long compId) {
        log.info("Delete запрос на удаление подборки");
        compilationService.delete(compId);
    }


}
