package ru.practicum.ewm.main.mappers;

import ru.practicum.ewm.main.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.model.compilation.Compilation;

public class CompilationMapper {
    public static Compilation toEntity(NewCompilationDto compilationDto) {
        return new Compilation(null, compilationDto.isPinned(), compilationDto.getTitle());
    }

    public static CompilationDto toDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(), compilation.isPinned(), compilation.getTitle());
    }
}
