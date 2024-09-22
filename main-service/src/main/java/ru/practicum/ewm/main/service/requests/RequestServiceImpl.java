package ru.practicum.ewm.main.service.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.main.exceptions.errors.ConflictException;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.mappers.RequestMapper;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.event.EventStatus;
import ru.practicum.ewm.main.model.request.Request;
import ru.practicum.ewm.main.model.request.RequestStatus;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.repository.EventRepository;
import ru.practicum.ewm.main.repository.RequestRepository;
import ru.practicum.ewm.main.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public List<ParticipationRequestDto> getRequest(Long userId) {
        List<Request> requests = requestRepository.findByRequesterIdAndNotInitiatorId(userId);
        return requests.stream().map(RequestMapper::toDto).toList();
    }

    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        List<Request> re = requestRepository.findAllByRequesterIdAndEventId(userId, eventId);
        if (!re.isEmpty()) {
            throw new ConflictException("Запрос на участие уже создан");
        }
        User user = getUserById(userId);
        Event event = getEventById(eventId);

        if (event.getInitiator() == user) {
            throw new ConflictException("Инициатор не может подать запрос на участие");
        }

        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new ConflictException("Событие не опубликовано");
        }
        Request request;

        if (!event.getRequestModeration()) {
            if (event.getParticipantLimit() != 0) {
                int countRequest = requestRepository.findAllByEventIdAndStatus(eventId, RequestStatus.CONFIRMED).size();
                if (countRequest >= event.getParticipantLimit()) {
                    throw new ConflictException("у события достигнут лимит запросов на участие");
                }
            }
            request = new Request(null, LocalDateTime.now(), event, user, RequestStatus.CONFIRMED);
        } else {
            if (event.getParticipantLimit() != 0) {
                request = new Request(null, LocalDateTime.now(), event, user, RequestStatus.PENDING);
            } else {
                request = new Request(null, LocalDateTime.now(), event, user, RequestStatus.CONFIRMED);
            }
        }
        requestRepository.save(request);
        return RequestMapper.toDto(request);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestsId) {
        getUserById(userId);
        Request request = getRequestById(requestsId);
        if (!request.getRequester().getId().equals(userId)) {
            throw new EntityNotFoundException("Запрос не найден");
        }
        request.setStatus(RequestStatus.CANCELED);
        requestRepository.save(request);
        return RequestMapper.toDto(request);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.error("Попытка создание события для несуществующего пользователя");
            throw new EntityNotFoundException("Нет пользователя с ид: " + userId);
        });
    }

    private Request getRequestById(Long requestsId) {
        return requestRepository.findById(requestsId).orElseThrow(() -> {
            log.error("Попытка изменения статуса не существующего запроса");
            throw new EntityNotFoundException("Нет запроса с ид: " + requestsId);
        });
    }

    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Попытка изменения статуса не существующего события");
            throw new EntityNotFoundException("Нет события с ид: " + eventId);
        });
    }
}
