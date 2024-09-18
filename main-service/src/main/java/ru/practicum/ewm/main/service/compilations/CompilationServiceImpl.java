package ru.practicum.ewm.main.service.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.mappers.CompilationMappers;
import ru.practicum.ewm.main.mappers.EventMappers;
import ru.practicum.ewm.main.model.compilation.Compilation;
import ru.practicum.ewm.main.model.compilation.CompilationEvent;
import ru.practicum.ewm.main.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.model.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main.model.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.event.dto.EventShortDto;
import ru.practicum.ewm.main.repository.CompilationRepository;
import ru.practicum.ewm.main.repository.CompilationsEventRepository;
import ru.practicum.ewm.main.validate.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationsEventRepository compilationsEventRepository;
    private final Validate validate;

    @Override
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMappers.toModel(newCompilationDto);
        compilationRepository.save(compilation);

        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            saveCompilationEvents(newCompilationDto.getEvents(), compilation);
        }
        CompilationDto compilationDto = mapToCompilationDtoWithEvents(compilation);
        log.info("Добавлена подборка: {}", compilationDto);
        return compilationDto;
    }

    private CompilationDto mapToCompilationDtoWithEvents(Compilation compilation) {
        List<CompilationEvent> compilationEventList = compilationsEventRepository.findAllByCompilationId(compilation.getId());
        List<EventShortDto> events = new ArrayList<>();
        for (CompilationEvent compilationEvent : compilationEventList) {
            events.add(EventMappers.toShortDto(compilationEvent.getEvent()));
        }
        CompilationDto compilationDto = CompilationMappers.toDto(compilation);
        compilationDto.setEvents(events);
        return compilationDto;
    }

    private void saveCompilationEvents(List<Long> eventIds, Compilation compilation) {
        for (Long eventId : eventIds) {
            Event event = validate.getEventById(eventId);
            CompilationEvent compilationEvent = new CompilationEvent();
            compilationEvent.setCompilation(compilation);
            compilationEvent.setEvent(event);
            compilationsEventRepository.save(compilationEvent);
        }
    }

    @Override
    public CompilationDto update(Long l, UpdateCompilationRequestDto updateCompilationRequestDto) {
        Compilation compilation = validate.getCompilationsById(l);
        if (updateCompilationRequestDto.getTitle() != null) {
            compilation.setTitle(updateCompilationRequestDto.getTitle());
        }
        if (updateCompilationRequestDto.getPinned() != null) {
            compilation.setPinned(updateCompilationRequestDto.getPinned());
        }

        if (updateCompilationRequestDto.getEvents() != null) {
            compilationsEventRepository.deleteAllByCompilationId(l);
            if (!updateCompilationRequestDto.getEvents().isEmpty()) {
                saveCompilationEvents(updateCompilationRequestDto.getEvents(), compilation);
            }
        }

        CompilationDto compilationDto = mapToCompilationDtoWithEvents(compilation);
        log.info("Подборка обновлена ид: {}", compilationDto);
        return compilationDto;
    }

    @Override
    public void deleteCompilation(Long compId) {
        Compilation compilation = validate.getCompilationsById(compId);
        compilationRepository.delete(compilation);
        compilationsEventRepository.deleteAllByCompilationId(compId);
        log.info("Подборка удалена ид: {}", compId);
    }

    @Override
    public CompilationDto getById(long compId) {
        Compilation compilation = validate.getCompilationsById(compId);
        List<CompilationEvent> compilationEvents = compilationsEventRepository.findAllByCompilationId(compId);
        CompilationDto compilationDto = CompilationMappers.toDto(compilation);
        List<EventShortDto> events = new ArrayList<>();
        if (!compilationEvents.isEmpty()) {
            for (CompilationEvent e : compilationEvents) {
                events.add(EventMappers.toShortDto(e.getEvent()));
            }
        }
        compilationDto.setEvents(events);
        return compilationDto;
    }

    @Override
    public List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Compilation> compilations = pinned == null ?
                compilationRepository.findAll(pageable).getContent()
                : compilationRepository.findAllByPinned(pinned, pageable);

        log.info("Retrieved {} compilations with pinned={} from index {} with page size {}",
                compilations.size(), pinned, from, size);

        return compilations.stream()
                .map(this::toCompilationDtoWithEvents).toList();
    }

    private CompilationDto toCompilationDtoWithEvents(Compilation compilation) {
        List<CompilationEvent> compilationEvents = compilationsEventRepository.findAllByCompilationId(compilation.getId());
        List<EventShortDto> events = compilationEvents.stream()
                .map(compilationEvent -> EventMappers.toShortDto(validate.getEventById(compilationEvent.getEvent().getId())))
                .collect(Collectors.toList());
        CompilationDto compilationDto = CompilationMappers.toDto(compilation);
        compilationDto.setEvents(events);
        return compilationDto;
    }

}
