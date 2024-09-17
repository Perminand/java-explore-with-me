package ru.practicum.ewm.main.validate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.main.exceptions.BadRequestException;
import ru.practicum.ewm.main.exceptions.NotFoundException;
import ru.practicum.ewm.main.model.Request;
import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.model.compilation.Compilation;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.repository.*;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class Validate {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final CompilationRepository compilationRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> {
            log.error("Попытка создание события для несуществующего пользователя");
            return new NotFoundException("Нет пользователя с ид: " + userId);
        });
    }

    public Category getCategoryById(Long l) {
        return categoryRepository.findById(l).orElseThrow(()-> {
            log.error("Попытка создание события для несуществующей категории");
            return new NotFoundException("Нет категории с ид: " + l);
        });
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(()-> {
            log.error("Попытка изменения статуса не существующего события");
            return new NotFoundException("Нет события с ид: " + eventId);
        });
    }

    public Request getRequestById(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(()-> {
            log.error("Попытка изменения статуса не существующего запроса");
            return new NotFoundException("Нет запроса с ид: " + requestId);
        });
    }

    public Compilation getCompilationsById(Long compilationsId) {
        return compilationRepository.findById(compilationsId).orElseThrow(() -> {
            log.error("Попытка изменения статуса не существующего события");
            return new NotFoundException("Нет события с ид: " + compilationsId);
        });
    }

    public void validateDates(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return;
        }
        if (start.isAfter(end)) {
            log.warn("Prohibited. Start is after end. Start: {}, end: {}", start, end);
            throw new BadRequestException("Event must be published");
        }
    }
}
