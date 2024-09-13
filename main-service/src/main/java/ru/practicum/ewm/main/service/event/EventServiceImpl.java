package ru.practicum.ewm.main.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.exceptions.errors.ConflictException;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.mappers.EventMappers;
import ru.practicum.ewm.main.model.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.model.ParticipationRequestDto;
import ru.practicum.ewm.main.model.State;
import ru.practicum.ewm.main.model.UpdateEventAdminRequest;
import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.event.dto.EventDto;
import ru.practicum.ewm.main.model.event.dto.EventFullDto;
import ru.practicum.ewm.main.model.event.dto.EventShortDto;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.repository.CategoryRepository;
import ru.practicum.ewm.main.repository.EventRepository;
import ru.practicum.ewm.main.repository.LocationRepository;
import ru.practicum.ewm.main.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    @Override
    public EventFullDto create(Long userId, EventDto eventDto) {
        User user = validateUser(userId);
        Category category = validateCategory(eventDto.getCategory());
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
        event.setCreatedOn(LocalDateTime.now());
        event.setLocation(locationRepository.save(eventDto.getLocation()));
        Event eventFinal = eventRepository.save(event);
        EventFullDto eventFullDto = EventMappers.toEventFullDto(eventFinal);
        return eventFullDto;
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

    @Override
    public EventFullDto update(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = validateEvent(eventId);
        if(event.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new ConflictException("Осталось менее часа до мероприятия");        }
        return null;
        if (!(updateEventAdminRequest.getStateAction().equals(State.PUBLISHED)
                && event.getState().equals(State.PENDING))
                || !(updateEventAdminRequest.getStateAction().equals(State.CANCELED)
            && event.getState().equals(State.PENDING))) {
            throw new ConflictException("")
        }
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
        return userRepository.findById(userId).orElseThrow(()-> {
            log.error("Попытка создание события для несуществующего пользователя");
            return new EntityNotFoundException("Нет пользователя с ид: " + userId);
        });
    }

    private Category validateCategory(Long l) {
        return categoryRepository.findById(l).orElseThrow(()-> {
            log.error("Попытка создание события для несуществующей category");
            return new EntityNotFoundException("Нет category с ид: " + l);
        });
    }

    private Event validateEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(()-> {
            log.error("Попытка изменения статуса не существующего события");
            return new EntityNotFoundException("Нет события с ид: " + eventId);
        });
    }

    private LocalDateTime validationEventDate(LocalDateTime eventDate) {
        if (eventDate.minusHours(2).isBefore(LocalDateTime.now())) {
            throw new ConflictException("eventDate должна содержать дату и время которое не наступила + 2 часа");
        }
        return eventDate;

    }
}
