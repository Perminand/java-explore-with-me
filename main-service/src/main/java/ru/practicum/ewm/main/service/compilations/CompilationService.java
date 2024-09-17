package ru.practicum.ewm.main.service.compilations;

import ru.practicum.ewm.main.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.model.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main.model.compilation.dto.UpdateCompilationRequestDto;

import java.util.List;

public interface CompilationService {

    CompilationDto create(NewCompilationDto newCompilationDto);

    CompilationDto update(Long l, UpdateCompilationRequestDto updateCompilationRequestDto);

    void deleteCompilation(Long compId);

    CompilationDto getById(long compId);

    List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);
}
