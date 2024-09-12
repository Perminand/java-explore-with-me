package ru.practicum.ewm.main.service.compilations;

import ru.practicum.ewm.main.model.compilation.CompilationDto;

public interface CompilationService {

    CompilationDto create(CompilationDto compilationDto);

    CompilationDto update(Long l, CompilationDto compilationDto);

    void delete(Long l);

    CompilationDto getById(long compId);

    CompilationDto getAll(Boolean pinned, Integer from, Integer size);
}
