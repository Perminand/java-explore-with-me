package ru.practicum.ewm.main.controllers.publicApi;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.service.compilations.CompilationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/compilations", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class CompilationPublicController {
    private final CompilationService compilationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getAll(
            @PathVariable(required = false)
            @RequestParam(required = false) Boolean pinned,
            @Min(0) @RequestParam(required = false, defaultValue = "0") Integer from,
            @Min(0) @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Get запрос на получение подборок");
        return compilationService.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto get(@Min(0) @PathVariable long compId) {
        log.info("Get запрос на получение подборки");
        return compilationService.getById(compId);
    }
}
