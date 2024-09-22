package ru.practicum.ewm.main.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.StatsDto;
import ru.practicum.ewm.StatsClient;
import ru.practicum.ewm.main.common.Constants;
import ru.practicum.ewm.main.common.Utilities;
import ru.practicum.ewm.main.dto.EventParamsLongDto;
import ru.practicum.ewm.main.dto.EventParamsShortDto;
import ru.practicum.ewm.main.dto.event.EventDto;
import ru.practicum.ewm.main.dto.event.EventFullDto;
import ru.practicum.ewm.main.dto.event.EventIdByRequestsCount;
import ru.practicum.ewm.main.dto.event.EventShortDto;
import ru.practicum.ewm.main.dto.eventRequest.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main.dto.eventRequest.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.main.dto.request.UpdateEventAdminRequestDto;
import ru.practicum.ewm.main.exceptions.errors.ConflictException;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.exceptions.errors.ValidationException;
import ru.practicum.ewm.main.mappers.EventMapper;
import ru.practicum.ewm.main.mappers.RequestMapper;
import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.event.EventStatus;
import ru.practicum.ewm.main.model.request.Request;
import ru.practicum.ewm.main.model.request.RequestStatus;
import ru.practicum.ewm.main.model.status.StateAction;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.repository.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Import({StatsClient.class})
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final StatsClient statClient;
    private final Utilities utilities;


    @Override
    @Transactional
    public EventFullDto create(Long userId, EventDto eventDto) {
        User user = getUserById(userId);
        Category category = getCategoryById(eventDto.getCategory());
        if (eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Время события должно больше текущего времени на 2 часа");
        }
        Utilities.setValueIfNull(eventDto.getRequestModeration(), true);
        Utilities.setValueIfNull(eventDto.getPaid(), false);
        Utilities.setValueIfNull(eventDto.getParticipantLimit(), 0);

        Event event = EventMapper.toEntity(eventDto);
        event.setInitiator(user);
        event.setCategory(category);
        event.setCreatedOn(LocalDateTime.now());
        event.setLocation(locationRepository.save(eventDto.getLocation()));
        event.setState(EventStatus.PENDING);
        Event eventFinal = eventRepository.save(event);
        return EventMapper.toFullDto(eventFinal);
    }

    @Override
    @Transactional
    public EventFullDto update(Long eventId, UpdateEventAdminRequestDto updateRequestDto) {
        Event event = getEventById(eventId);

        LocalDateTime localDateTime;
        if (updateRequestDto.getEventDate() != null) {
            localDateTime = LocalDateTime.parse(
                    updateRequestDto.getEventDate(),
                    Constants.DATE_FORMATTER);
            if (localDateTime.minusHours(1).isBefore(LocalDateTime.now())) {
                throw new ValidationException("Время уже наступило или осталось менее часа до мероприятия");
            }
        }
        if (updateRequestDto.getStateAction() != null) {
            if (updateRequestDto.getStateAction().equals(StateAction.PUBLISH_EVENT) && event.getState().equals(EventStatus.PUBLISHED)) {
                throw new DataIntegrityViolationException("Event is already published");
            }
            if (updateRequestDto.getStateAction().equals(StateAction.PUBLISH_EVENT) && event.getState().equals(EventStatus.CANCELED)) {
                throw new DataIntegrityViolationException("Event is canceled");
            }
            if (updateRequestDto.getStateAction().equals(StateAction.REJECT_EVENT) && event.getState().equals(EventStatus.PUBLISHED)) {
                throw new DataIntegrityViolationException("Event is already published");
            }
            if (updateRequestDto.getStateAction().equals(StateAction.REJECT_EVENT)) {
                event.setState(EventStatus.CANCELED);
            } else {
                event.setState(EventStatus.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now().format(Constants.DATE_FORMATTER));
            }
        }

        Utilities.setValueIfNotNull(
                updateRequestDto.getAnnotation(), 
                event.getAnnotation(), 
                updateRequestDto.getAnnotation());

        Utilities.setValueIfNotNull(
                updateRequestDto.getCategory(), 
                event.getCategory(),
                categoryRepository.findById(updateRequestDto.getCategory()).get());

        Utilities.setValueIfNotNull(
                updateRequestDto.getDescription(), 
                event.getDescription(),
                updateRequestDto.getDescription());

        Utilities.setValueIfNotNull(
                updateRequestDto.getEventDate(),
                event.getEventDate(),
                LocalDateTime.parse(updateRequestDto.getEventDate(), Constants.DATE_FORMATTER));

        Utilities.setValueIfNotNull(
                updateRequestDto.getLocation(),
                event.getLocation(),
                locationRepository.save(updateRequestDto.getLocation()));

        Utilities.setValueIfNotNull(
                updateRequestDto.getPaid(),
                event.getPaid(),
                updateRequestDto.getPaid());

        Utilities.setValueIfNotNull(
                updateRequestDto.getParticipantLimit(),
                event.getParticipantLimit(),
                updateRequestDto.getParticipantLimit());

        Utilities.setValueIfNotNull(
                updateRequestDto.getRequestModeration(),
                event.getRequestModeration(),
                updateRequestDto.getRequestModeration());

        Utilities.setValueIfNotNull(
                updateRequestDto.getTitle(),
                event.getTitle(),
                updateRequestDto.getTitle());

        if (updateRequestDto.getStateAction() == StateAction.PUBLISH_EVENT) {
            event.setState(EventStatus.PUBLISHED);
            event.setPublishedOn(LocalDateTime.now().format(Constants.DATE_FORMATTER));
        } else if (updateRequestDto.getStateAction() == StateAction.REJECT_EVENT) {
            event.setState(EventStatus.CANCELED);
        }

        eventRepository.save(event);
        return EventMapper.toFullDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsByUser(Long userId, Integer from, Integer size) {
        getUserById(userId);
        List<EventShortDto> eventShortDtoList = eventRepository.findAllByInitiatorId(
                        userId,
                        Utilities.getPageable(from, size))
                .stream()
                .map(EventMapper::toShortDto)
                .toList();
        List<Long> eventsIds = eventShortDtoList.stream().map(EventShortDto::getId).toList();
        Map<Long, Long> confirmedRequestsByEvents = requestRepository
                .countByEventIdInAndStatusGroupByEvent(eventsIds, String.valueOf(RequestStatus.CONFIRMED))
                .stream()
                .collect(Collectors.toMap(EventIdByRequestsCount::getEvent, EventIdByRequestsCount::getCount));

        List<Long> views = getViews(eventsIds);

        List<EventShortDto> eventsForResp =
                Utilities.addViewsAndConfirmedRequestsShort(eventShortDtoList, confirmedRequestsByEvents, views);

        return Utilities.checkTypes(eventsForResp,
                EventShortDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventByUserId(Long userId, Long eventId) {
        getUserById(userId);
        getEventById(eventId);
        return EventMapper.toFullDto(Optional.of(eventRepository.findByIdAndInitiatorId(eventId, userId)).orElseThrow(() -> {
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
                .map(RequestMapper::toDto)
                .toList();
    }

    @Override
    public EventFullDto updateEventByUserId(Long userId, Long eventId, UpdateEventAdminRequestDto request) {
        getUserById(userId);
        Event event = getEventById(eventId);
        if (request.getStateAction() == StateAction.SEND_TO_REVIEW) {
            event.setState(EventStatus.PENDING);
        }
        if (request.getCategory() != null) {
            Category category = getCategoryById(request.getCategory());
            event.setCategory(category);
        }
        if (event.getState() == EventStatus.PUBLISHED) {
            throw new ConflictException("Событие уже опубликовано");
        } else if (event.getState() == EventStatus.CANCELED) {
            throw new ConflictException("Событие уже отменено");
        } else {
            if (request.getStateAction() != null) {
                if (request.getStateAction().equals(StateAction.REJECT_EVENT)) {
                    event.setState(EventStatus.REJECTED);
                } else if (request.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                    event.setState(EventStatus.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now().format(Constants.DATE_FORMATTER));
                } else if (request.getStateAction().equals(StateAction.CANCEL_REVIEW)) {
                    event.setState(EventStatus.CANCELED);
                }
            }
        }
        if (request.getEventDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.parse(request.getEventDate(), Constants.DATE_FORMATTER);
            if (localDateTime.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ValidationException("Время события не может быть раньше чем за 2 часа");
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
        return EventMapper.toFullDto(event);
    }

    @Override
    public EventRequestStatusUpdateResult updateStatusRequestEventByUser(Long userId,
                                                                         Long eventId,
                                                                         EventRequestStatusUpdateRequest request) {
        getUserById(userId);
        Event event = getEventById(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new EntityNotFoundException("Нет данного события у пользователя");
        }

        List<ParticipationRequestDto> confirmedReqs = new ArrayList<>();
        List<ParticipationRequestDto> canceledReqs = new ArrayList<>();

        List<Request> requestList = requestRepository.findAllByIdInAndStatus(request.getRequestIds(), RequestStatus.PENDING);

        if (event.getParticipantLimit() != 0 || event.getRequestModeration()) {
            Long countRequest = requestRepository.countByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);
            if (event.getParticipantLimit() <= countRequest) {
                throw new ConflictException("нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие");
            }

            for (int i = 0; i < requestList.size(); i++) {
                if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
                    if (event.getParticipantLimit() - countRequest > i) {
                        requestList.get(i).setStatus(request.getStatus());
                        confirmedReqs.add(RequestMapper.toDto(requestList.get(i)));
                    } else {
                        requestList.get(i).setStatus(RequestStatus.REJECTED);
                        canceledReqs.add(RequestMapper.toDto(requestList.get(i)));
                    }
                } else {
                    if (requestList.get(i).getStatus().equals(RequestStatus.CONFIRMED)) {
                        throw new ConflictException("Заявка уже принята");
                    }
                    requestList.get(i).setStatus(RequestStatus.REJECTED);
                    canceledReqs.add(RequestMapper.toDto(requestList.get(i)));
                }
            }
        } else {
            for (Request r : requestList) {
                r.setStatus(request.getStatus());
                confirmedReqs.add(RequestMapper.toDto(r));
            }
        }
        return new EventRequestStatusUpdateResult(confirmedReqs, canceledReqs);
    }


    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsShort(EventParamsShortDto paramDto) {
        LocalDateTime start = convertToLocalDataTime(decode(paramDto.getRangeStart()));
        LocalDateTime end = convertToLocalDataTime(decode(paramDto.getRangeEnd()));
        validateDates(start, end);

        Utilities.setValueIfNull(paramDto.getText(), "");
        Utilities.setValueIfNull(paramDto.getCategories(), List.of());

        if (start == null) {
            start = LocalDateTime.now();
        }
        if (end == null) {
            end = Constants.defaultEndTime;
        }

        List<Event> eventList = eventRepository
                .searchEvents(
                        paramDto.getText(),
                        paramDto.getCategories(),
                        paramDto.getPaid(),
                        start,
                        end,
                        paramDto.getOnlyAvailable(),
                        Utilities.getPageable(paramDto.getFrom(), paramDto.getSize()));

        List<EventShortDto> events = eventRepository
                .searchEvents(
                        paramDto.getText(),
                        paramDto.getCategories(),
                        paramDto.getPaid(),
                        start,
                        end,
                        paramDto.getOnlyAvailable(),
                        Utilities.getPageable(paramDto.getFrom(), paramDto.getSize()))
                .stream()
                .map(EventMapper::toShortDto)
                .toList();

        List<Long> eventsIds = events.stream()
                .map(EventShortDto::getId)
                .toList();

        Map<Long, Long> confirmedRequestsByEvents = requestRepository
                .countByEventIdInAndStatusGroupByEvent(eventsIds, String.valueOf(RequestStatus.CONFIRMED))
                .stream()
                .collect(Collectors.toMap(EventIdByRequestsCount::getEvent, EventIdByRequestsCount::getCount));

        List<Long> views = getViews(eventsIds);

        List<EventShortDto> eventsForResp =
                Utilities.addViewsAndConfirmedRequestsShort(events, confirmedRequestsByEvents, views);

        return Utilities.checkTypes(eventsForResp, EventShortDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEvent(Long id) {
        Event event = getEventById(id);
        if (event.getState().equals(EventStatus.PUBLISHED)) {
            EventFullDto eventFullDto = EventMapper.toFullDto(event);
            eventFullDto.setConfirmedRequests(requestRepository.countByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED));
            return eventFullDto;
        } else {
            String error = "Нет события с заданным ид: " + id;
            log.error(error);
            throw new EntityNotFoundException(error);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getEventsFull(
            EventParamsLongDto paramsDto) {
        LocalDateTime rangeStartL;
        LocalDateTime rangeEndL;
        Utilities.setValueIfNull(paramsDto.getInitiator(), new ArrayList<>());
        Utilities.setValueIfNull(paramsDto.getState(), new ArrayList<>());
        Utilities.setValueIfNull(paramsDto.getCategory(), new ArrayList<>());

        if (paramsDto.getRangeStart() != null) {
            rangeStartL = LocalDateTime.parse(paramsDto.getRangeStart(), Constants.DATE_FORMATTER);
        } else {
            rangeStartL = Constants.defaultStartTime;
        }
        if (paramsDto.getRangeStart() != null) {
            rangeEndL = LocalDateTime.parse(paramsDto.getRangeStart(), Constants.DATE_FORMATTER);
        } else {
            rangeEndL = Constants.defaultEndTime;
        }
        List<Event> event = eventRepository.findAllByInitiatorIdInAndCategoryIdInAndStateInAndDateTimeBetween(
                paramsDto.getInitiator(),
                paramsDto.getCategory(),
                paramsDto.getState(),
                rangeStartL,
                rangeEndL,
                Utilities.getPageable(paramsDto.getFrom(), paramsDto.getSize()));

        List<EventFullDto> eventFullDtoList = event.stream().map(EventMapper::toFullDto).toList();
        List<Long> eventsIds = eventFullDtoList.stream().map(EventFullDto::getId).toList();

        Map<Long, Long> confirmedRequestsByEvents = requestRepository
                .countByEventIdInAndStatusGroupByEvent(eventsIds, String.valueOf(RequestStatus.CONFIRMED))
                .stream()
                .collect(Collectors.toMap(EventIdByRequestsCount::getEvent, EventIdByRequestsCount::getCount));

        List<Long> views = getViews(eventsIds);

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

    private Category getCategoryById(Long category) {
        return categoryRepository.findById(category).orElseThrow(() -> {
            log.error("Попытка создание события для несуществующей категории");
            throw new EntityNotFoundException("Нет категории с ид: " + category);
        });
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.error("Попытка создание события для несуществующего пользователя");
            throw new EntityNotFoundException("Нет пользователя с ид: " + userId);
        });
    }

    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Попытка изменения статуса не существующего события");
            throw new EntityNotFoundException("Нет события с ид: " + eventId);
        });
    }

    private List<Long> getViews(List<Long> eventsIds) {
        String stringIds = eventsIds
                .stream()
                .map((id) -> "event/" + id).collect(Collectors.joining());

        return statClient.getStats(Constants.defaultStartTime, Constants.defaultEndTime,
                        stringIds, true).stream()
                .map(StatsDto::getHits)
                .collect(Collectors.toList());
    }

    private void validateDates(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return;
        }
        if (start.isAfter(end)) {
            log.warn("Prohibited. Start is after end. Start: {}, end: {}", start, end);
            throw new ValidationException("Event must be published");
        }
    }
}
