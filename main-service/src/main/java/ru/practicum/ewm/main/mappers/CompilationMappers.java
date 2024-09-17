package ru.practicum.ewm.main.mappers;

import ru.practicum.ewm.main.model.compilation.Compilation;
import ru.practicum.ewm.main.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.model.compilation.dto.NewCompilationDto;

public class CompilationMappers {
    public static Compilation toModel(NewCompilationDto compilationDto) {
        return new Compilation(null, compilationDto.isPinned(), compilationDto.getTitle());
    }

    public static CompilationDto toDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(), compilation.isPinned(), compilation.getTitle());
    }
}
