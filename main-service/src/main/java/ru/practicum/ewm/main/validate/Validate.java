package ru.practicum.ewm.main.validate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.repository.CategoryRepository;
import ru.practicum.ewm.main.repository.EventRepository;
import ru.practicum.ewm.main.repository.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class Validate {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    public User validateUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> {
            log.error("Попытка создание события для несуществующего пользователя");
            return new EntityNotFoundException("Нет пользователя с ид: " + userId);
        });
    }

    public Category validateCategory(Long l) {
        return categoryRepository.findById(l).orElseThrow(()-> {
            log.error("Попытка создание события для несуществующей category");
            return new EntityNotFoundException("Нет category с ид: " + l);
        });
    }

    public Event validateEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(()-> {
            log.error("Попытка изменения статуса не существующего события");
            return new EntityNotFoundException("Нет события с ид: " + eventId);
        });
    }
}
