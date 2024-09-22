package ru.practicum.ewm.main.service.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.common.Utilities;
import ru.practicum.ewm.main.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.dto.compilation.UpdateCompilationRequestDto;
import ru.practicum.ewm.main.dto.event.EventShortDto;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.mappers.CompilationMapper;
import ru.practicum.ewm.main.mappers.EventMapper;
import ru.practicum.ewm.main.model.compilation.Compilation;
import ru.practicum.ewm.main.model.compilation.CompilationEvent;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.repository.CompilationRepository;
import ru.practicum.ewm.main.repository.CompilationsEventRepository;
import ru.practicum.ewm.main.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;
    private final CompilationsEventRepository compilationsEventRepository;

    @Override
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.toEntity(newCompilationDto);
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
            events.add(EventMapper.toShortDto(compilationEvent.getEvent()));
        }
        CompilationDto compilationDto = CompilationMapper.toDto(compilation);
        compilationDto.setEvents(events);
        return compilationDto;
    }

    private void saveCompilationEvents(List<Long> eventIds, Compilation compilation) {
        for (Long eventId : eventIds) {
            Event event = getEventById(eventId);
            CompilationEvent compilationEvent = new CompilationEvent();
            compilationEvent.setCompilation(compilation);
            compilationEvent.setEvent(event);
            compilationsEventRepository.save(compilationEvent);
        }
    }

    @Override
    public CompilationDto update(Long l, UpdateCompilationRequestDto updateCompilationRequestDto) {
        Compilation compilation = getCompilationsById(l);
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

    private Compilation getCompilationsById(Long l) {
        return compilationRepository.findById(l).orElseThrow(() -> {
            log.error("Попытка изменения статуса не существующего события");
            throw new EntityNotFoundException("Нет события с ид: " + l);
        });
    }

    @Override
    public void deleteCompilation(Long compId) {
        Compilation compilation = getCompilationsById(compId);
        compilationRepository.delete(compilation);
        compilationsEventRepository.deleteAllByCompilationId(compId);
        log.info("Подборка удалена ид: {}", compId);
    }

    @Override
    public CompilationDto getById(long compId) {
        Compilation compilation = getCompilationsById(compId);
        List<CompilationEvent> compilationEvents = compilationsEventRepository.findAllByCompilationId(compId);
        CompilationDto compilationDto = CompilationMapper.toDto(compilation);
        List<EventShortDto> events = new ArrayList<>();
        if (!compilationEvents.isEmpty()) {
            for (CompilationEvent e : compilationEvents) {
                events.add(EventMapper.toShortDto(e.getEvent()));
            }
        }
        compilationDto.setEvents(events);
        return compilationDto;
    }

    @Override
    public List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = Utilities.getPageable(from, size);
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
                .map(compilationEvent -> EventMapper.toShortDto(getEventById(compilationEvent.getEvent().getId())))
                .collect(Collectors.toList());
        CompilationDto compilationDto = CompilationMapper.toDto(compilation);
        compilationDto.setEvents(events);
        return compilationDto;
    }

    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Попытка изменения статуса не существующего события");
            throw new EntityNotFoundException("Нет события с ид: " + eventId);
        });
    }

}
