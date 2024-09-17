package ru.practicum.ewm.main.validate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.exceptions.errors.ValidationException;
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
            throw new EntityNotFoundException("Нет пользователя с ид: " + userId);
        });
    }

    public Category getCategoryById(Long l) {
        Category category = categoryRepository.findById(l).orElseThrow(() -> {
            log.error("Попытка создание события для несуществующей категории");
            throw new EntityNotFoundException("Нет категории с ид: " + l);
        });
        return category;
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(()-> {
            log.error("Попытка изменения статуса не существующего события");
            throw new EntityNotFoundException("Нет события с ид: " + eventId);
        });
    }

    public Request getRequestById(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(()-> {
            log.error("Попытка изменения статуса не существующего запроса");
            throw new EntityNotFoundException("Нет запроса с ид: " + requestId);
        });
    }

    public Compilation getCompilationsById(Long compilationsId) {
        return compilationRepository.findById(compilationsId).orElseThrow(() -> {
            log.error("Попытка изменения статуса не существующего события");
            throw new EntityNotFoundException("Нет события с ид: " + compilationsId);
        });
    }

    public void validateDates(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return;
        }
        if (start.isAfter(end)) {
            log.warn("Prohibited. Start is after end. Start: {}, end: {}", start, end);
            throw new ValidationException("Event must be published");
        }
    }
}
