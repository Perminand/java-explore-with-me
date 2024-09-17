package ru.practicum.ewm.main.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.StatClientImp;
import ru.practicum.ewm.main.common.ConnectToStatServer;
import ru.practicum.ewm.main.common.GeneralConstants;
import ru.practicum.ewm.main.common.Utilities;
import ru.practicum.ewm.main.exceptions.errors.ConflictException;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.mappers.EventMappers;
import ru.practicum.ewm.main.mappers.RequestMappers;
import ru.practicum.ewm.main.model.*;
import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.event.dto.EventDto;
import ru.practicum.ewm.main.model.event.dto.EventFullDto;
import ru.practicum.ewm.main.model.event.dto.EventIdByRequestsCount;
import ru.practicum.ewm.main.model.event.dto.EventShortDto;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.repository.*;
import ru.practicum.ewm.main.validate.Validate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Import({StatClientImp.class})
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final Validate validate;
    private final StatClientImp statClientImp;

    @Override
    @Transactional
    public EventFullDto create(Long userId, EventDto eventDto) {
        User user = validate.getUserById(userId);
        Category category = validate.getCategoryById(eventDto.getCategory());
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
        event.setState(State.PENDING);
        Event eventFinal = eventRepository.save(event);
        return EventMappers.toEventFullDto(eventFinal);
    }

    @Override
    @Transactional
    public EventFullDto update(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = validate.getEventById(eventId);

        if(event.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new ConflictException("Осталось менее часа до мероприятия");
        }

        if (updateEventAdminRequest.getStateAction().equals(StateAction.PUBLISH_EVENT)
                && !(event.getState().equals(State.PENDING))) {
            log.error("Попытка опубликовать событие в статусах отличных от ожидающих");
            throw new ConflictException("Опубликовать можно только ожидающие публикацию событие");
        }

        if (updateEventAdminRequest.getStateAction().equals(StateAction.REJECT_EVENT)
                && !(event.getState().equals(State.PENDING.toString()))) {
            log.error("Попытка отменить событие в статусах отличных от ожидающих");
            throw new ConflictException("Отменить можно только ожидающие публикацию событие");
        }

        if (updateEventAdminRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        }

        if (updateEventAdminRequest.getCategory() != null) {
            event.setCategory(categoryRepository.findById(updateEventAdminRequest.getCategory()).get());
        }

        if (updateEventAdminRequest.getDescription() != null) {
            event.setDescription(updateEventAdminRequest.getDescription());
        }

        if (updateEventAdminRequest.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(updateEventAdminRequest.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        if (updateEventAdminRequest.getLocation() != null) {
            event.setLocation(locationRepository.save(updateEventAdminRequest.getLocation()));
        }

        if (updateEventAdminRequest.getPaid() != null) {
            event.setPaid(updateEventAdminRequest.getPaid());
        }

        if (updateEventAdminRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }

        if (updateEventAdminRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        }

        if (updateEventAdminRequest.getTitle() != null) {
            event.setTitle(updateEventAdminRequest.getTitle());
        }

        if (updateEventAdminRequest.getStateAction() == StateAction.PUBLISH_EVENT) {
            event.setState(State.PUBLISHED);
        } else if (updateEventAdminRequest.getStateAction() == StateAction.REJECT_EVENT) {
            event.setState(State.CANCELED);
        }

                eventRepository.save(event);
        return EventMappers.toEventFullDto(event);
    }



    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsByUser(Long userId, Integer from, Integer size) {
        validate.getUserById(userId);
        int startPage = from > 0 ? (from/size) : 0;
        Pageable pageable = PageRequest.of(startPage, size);
        List<EventShortDto> eventShortDtoList = eventRepository.findAllByInitiatorId(userId, pageable).stream().map(EventMappers::toShortDto).toList();
        List<Long> longList = eventShortDtoList.stream().map(EventShortDto::getId).toList();
        Map<Long, Long> confirmedRequestsByEvents = requestRepository
                .countByEventIdInAndStatusGroupByEvent(longList, String.valueOf(RequestStatus.CONFIRMED))
                .stream()
                .collect(Collectors.toMap(EventIdByRequestsCount::getEvent, EventIdByRequestsCount::getCount));

        List<Long> views = ConnectToStatServer.getViews(GeneralConstants.defaultStartTime, GeneralConstants.defaultEndTime,
                ConnectToStatServer.prepareUris(longList), true, statClientImp);

        List<EventShortDto> eventsForResp =
                Utilities.addViewsAndConfirmedRequestsShort(eventShortDtoList, confirmedRequestsByEvents, views);

        return Utilities.checkTypes(eventsForResp,
                EventShortDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventByUserId(Long userId, Long eventId) {
        validate.getUserById(userId);
        validate.getEventById(eventId);
        return EventMappers.toEventFullDto(Optional.of(eventRepository.findByIdAndInitiatorId(eventId, userId)).orElseThrow(() -> {
            String error = "Нет события с ид: " + eventId + " созданным пользователем: " + userId;
            log.error(error);
            return new EntityNotFoundException(error);
        }));

    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> requestEventByUserId(Long userId, Long eventId) {
        return requestRepository.findAllByEventIdAndInitiatorId(eventId, userId)
                .stream()
                .map(RequestMappers::toDto)
                .toList();
    }

    @Override
    public EventFullDto updateEventByUserId(Long userId, Long eventId, UpdateEventAdminRequest request) {
        validate.getUserById(userId);
        Event event = validate.getEventById(eventId);
        if (request.getCategory() != null) {
            Category category = validate.getCategoryById(request.getCategory());
            event.setCategory(category);
        }
        if (event.getState() == State.PUBLISHED) {
            throw new ConflictException("Событие уже опубликовано");
        } else if(event.getState() == State.CANCELED) {
            throw new ConflictException("Событие уже отменено");
        } else {
            if(request.getStateAction().equals(StateAction.REJECT_EVENT)) {
                event.setState(State.REJECTED);
            } else if (request.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now().format(GeneralConstants.DATE_FORMATTER));
            } else if (request.getStateAction().equals(StateAction.CANCEL_REVIEW)) {
                event.setState(State.CANCELED);
            }
        }
        if (request.getEventDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.parse(request.getEventDate(), GeneralConstants.DATE_FORMATTER);
            if (localDateTime.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ConflictException("Время события не может быть раньше чем за 2 часа");
            }
            event.setEventDate(localDateTime);
        }
        if (request.getAnnotation() != null) {
            event.setAnnotation(request.getAnnotation());
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getPaid() != null) {
            event.setPaid(request.getPaid());
        }
        if (request.getParticipantLimit() != null) {
            event.setParticipantLimit(request.getParticipantLimit());
        }
        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
        }
        eventRepository.save(event);
        return EventMappers.toEventFullDto(event);
    }

    @Override
    public EventRequestStatusUpdateResult updateStatusRequestEventByUser(Long userId,
                                                                         Long eventId,
                                                                         EventRequestStatusUpdateRequest request) {
        validate.getUserById(userId);
        Event event = validate.getEventById(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new EntityNotFoundException("Нет данного события у пользователя");
        }

        List<ParticipationRequestDto> confirmedReqs = new ArrayList<>();
        List<ParticipationRequestDto> canceledReqs = new ArrayList<>();

        List<Request> requestList = requestRepository.findAllByIdInAndStatus(request.getRequestIds(), State.PENDING);

        if (event.getParticipantLimit() != 0 || event.getRequestModeration()) {
            int countRequest = requestRepository.countByEventIdAndStatus(eventId, State.REJECTED);
            if (event.getParticipantLimit() == countRequest) {
                throw new ConflictException("нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие");
            }

            for (int i = 0; i < requestList.size(); i++) {
                if (request.getStatus().equals(State.CONFIRMED)) {
                    if (event.getParticipantLimit() - countRequest > i) {
                        requestList.get(i).setStatus(request.getStatus());
                        confirmedReqs.add(RequestMappers.toDto(requestList.get(i)));
                    } else {
                        requestList.get(i).setStatus(State.REJECTED);
                        canceledReqs.add(RequestMappers.toDto(requestList.get(i)));
                    }
                } else {
                    requestList.get(i).setStatus(State.REJECTED);
                    canceledReqs.add(RequestMappers.toDto(requestList.get(i)));
                }
            }
        } else {
            for(Request r : requestList) {
                r.setStatus(request.getStatus());
                confirmedReqs.add(RequestMappers.toDto(r));
            }
        }
        return new EventRequestStatusUpdateResult(confirmedReqs, canceledReqs);
    }
        


    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsFilter(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        LocalDateTime start = convertToLocalDataTime(decode(rangeStart));
        LocalDateTime end = convertToLocalDataTime(decode(rangeEnd));
        validate.validateDates(start, end);
        int startPage = from > 0 ? (from / size) : 0;
        Pageable pageable = PageRequest.of(startPage, size);

        if (text == null) {
            text = "";
        }
        if (categories == null) {
            categories = List.of();
        }
        if (start == null) {
            start = LocalDateTime.now();
        }
        if (end == null) {
            end = GeneralConstants.defaultEndTime;
        }

        List<EventShortDto> events = eventRepository
                .searchEvents(text, categories, paid, start, end, onlyAvailable, pageable)
                .stream()
                .map(EventMappers::toShortDto)
                .toList();

        List<Long> eventsIds = events.stream()
                .map(EventShortDto::getId)
                .toList();

        Map<Long, Long> confirmedRequestsByEvents = requestRepository
                .countByEventIdInAndStatusGroupByEvent(eventsIds, String.valueOf(RequestStatus.CONFIRMED))
                .stream()
                .collect(Collectors.toMap(EventIdByRequestsCount::getEvent, EventIdByRequestsCount::getCount));

        List<Long> views = ConnectToStatServer.getViews(GeneralConstants.defaultStartTime,
                GeneralConstants.defaultEndTime, ConnectToStatServer.prepareUris(eventsIds),
                true, statClientImp);

        List<EventShortDto> eventsForResp =
                Utilities.addViewsAndConfirmedRequestsShort(events, confirmedRequestsByEvents, views);

        return Utilities.checkTypes(eventsForResp, EventShortDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEvent(Long id) {
        Event event = validate.getEventById(id);
        if (event.getState().equals(State.PUBLISHED)) {
            return EventMappers.toEventFullDto(event);
        } else {
            String error = "Нет события с заданным ид: " + id;
            log.error(error);
            throw new EntityNotFoundException(error);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getEvents(
            List<Long> initiator,
            List<String> state,
            List<Long> category,
            String rangeStart,
            String rangeEnd,
            Integer from,
            Integer size) {
        int startPage = from > 0 ? (from / size) : 0;
        Pageable pageable = PageRequest.of(startPage, size);
        LocalDateTime rangeStartL = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime rangeEndL = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Event> event = eventRepository.findAllByInitiatorIdInAndStateInAndCategoryIdInAndDateTimeBetween(
                initiator,
                category,
                state,
                rangeStartL,
                rangeEndL,
                pageable);

        List<EventFullDto> eventFullDtoList = event.stream().map(EventMappers::toEventFullDto).toList();
        List<Long> eventsIds = eventFullDtoList.stream().map(EventFullDto::getId).toList();

        Map<Long, Long> confirmedRequestsByEvents = requestRepository
                .countByEventIdInAndStatusGroupByEvent(eventsIds, String.valueOf(RequestStatus.CONFIRMED))
                .stream()
                .collect(Collectors.toMap(EventIdByRequestsCount::getEvent, EventIdByRequestsCount::getCount));

        List<Long> views = ConnectToStatServer.getViews(
                GeneralConstants.defaultStartTime,
                GeneralConstants.defaultEndTime,
                ConnectToStatServer.prepareUris(eventsIds), true, statClientImp);

        List<EventFullDto> events =
                Utilities.addViewsAndConfirmedRequestsFull(eventFullDtoList, confirmedRequestsByEvents, views);
        return Utilities.checkTypes(events, EventFullDto.class);
    }


    private LocalDateTime convertToLocalDataTime(String date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.parse(date, ru.practicum.GeneralConstants.DATE_FORMATTER);
    }

    private String decode(String parameter) {
        if (parameter == null) {
            return null;
        }
        return URLDecoder.decode(parameter, StandardCharsets.UTF_8);
    }
}
