package ru.practicum.ewm.main.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.exceptions.errors.ConflictException;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.mappers.CategoryMappers;
import ru.practicum.ewm.main.mappers.EventMappers;
import ru.practicum.ewm.main.model.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.model.ParticipationRequestDto;
import ru.practicum.ewm.main.model.UpdateEventAdminRequest;
import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.event.dto.EventDto;
import ru.practicum.ewm.main.model.event.dto.EventFullDto;
import ru.practicum.ewm.main.model.event.dto.EventShortDto;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.repository.CategoryRepository;
import ru.practicum.ewm.main.repository.EventRepository;
import ru.practicum.ewm.main.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public EventFullDto create(Long userId, EventDto eventDto) {
        User user = validateUser(userId);
        Category category = validateCategory(eventDto.getCategory());
        LocalDateTime eventTime = validationEventDate(eventDto.getEventDate());
        if (eventDto.getRequestModeration() == null) {
            eventDto.setRequestModeration(true);
        }
        if (eventDto.getPaid() == null) {
            eventDto.setPaid(false);
        }
        if (eventDto.getParticipantLimit() == null) {
            eventDto.setParticipantLimit(0);
        }
        Event event = EventMappers.toEvent(eventDto);
        event.setInitiator(user);
        event.setCategory(category);
        event.setEventDate(eventTime.toString());
        eventRepository.save(event);
        return EventMappers.toEventFullDto(event);
    }



    @Override
    public List<EventFullDto> get(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
        for(Long l : users) {
            validateUser(l);
        }
        for(Long l : categories) {
            validateCategory(l);
        }
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        int startPage = from > 0 ? (from / size) : 0;
        Pageable pageable = PageRequest.of(startPage, size, sortById);
//        List<Event> eventFullDtoList = eventRepository.getAllForFilter(users, states, categories, rangeStart, rangeEnd, pageable);
    return null;
    }

    private Category validateCategory(Long l) {
        return Optional.of(categoryRepository.findById(l)).get().orElseThrow(()-> {
            log.error("Попытка создание события для несуществующей category");
            throw new EntityNotFoundException("Нет category с ид: " + l);
        });
    }

    @Override
    public void update(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {

    }

    @Override
    public List<EventShortDto> getEventsByUser(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto getEventByUserId(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto requestEventByUserId(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto updateEventByUserId(Long userId, Long eventId, UpdateEventAdminRequest request) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateStatusRequestEventByUser(Long userId, Long eventId, EventRequestStatusUpdateResult result) {
        return null;
    }

    @Override
    public List<EventShortDto> getEventsFilter(String text, List<Long> categories, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto getEvent(Long id) {
        return null;
    }

    private User validateUser(Long userId) {
        return Optional.of(userRepository.findById(userId)).get().orElseThrow(()-> {
            log.error("Попытка создание события для несуществующего user");
            throw new EntityNotFoundException("Нет user с ид: " + userId);
        });
    }

    private LocalDateTime validationEventDate(String eventDate) {
        LocalDateTime eventTime = LocalDateTime.parse(eventDate, DateTimeFormatter.ISO_TIME);

        if (eventTime.minusHours(2).isBefore(LocalDateTime.now())) {
            throw new ConflictException("eventDate должна содержать дату и время которое не наступила + 2 часа");
        }
        return eventTime;

    }
}
