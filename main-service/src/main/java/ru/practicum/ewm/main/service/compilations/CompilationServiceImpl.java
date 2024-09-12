package ru.practicum.ewm.main.service.compilations;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.model.compilation.CompilationDto;

@Service
public class CompilationServiceImpl implements CompilationService{
    @Override
    public CompilationDto create(CompilationDto compilationDto) {
        return null;
    }

    @Override
    public CompilationDto update(Long l, CompilationDto compilationDto) {
        return null;
    }

    @Override
    public void delete(Long l) {

    }

    @Override
    public CompilationDto getById(long compId) {
        return null;
    }

    @Override
    public CompilationDto getAll(Boolean pinned, Integer from, Integer size) {
        return null;
    }
}
