package ru.practicum.ewm.main.service.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.exceptions.errors.ConflictException;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.mappers.RequestMappers;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.request.Request;
import ru.practicum.ewm.main.model.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.main.model.status.State;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.repository.RequestRepository;
import ru.practicum.ewm.main.validate.Validate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final Validate validate;
    private final RequestRepository requestRepository;

    @Override
    public List<ParticipationRequestDto> getRequest(Long userId) {
        List<Request> requests = requestRepository.findByRequesterIdAndNotInitiatorId(userId);
        return requests.stream().map(RequestMappers::toDto).toList();
    }

    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        List<Request> re = requestRepository.findAllByRequesterIdAndEventId(userId, eventId);
        if (!re.isEmpty()) {
            throw new ConflictException("Запрос на участие уже создан");
        }
        User user = validate.getUserById(userId);
        Event event = validate.getEventById(eventId);

        if (event.getInitiator() == user) {
            throw new ConflictException("Инициатор не может подать запрос на участие");
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Событие не опубликовано");
        }
        Request request;

        if (!event.getRequestModeration()) {
            if (event.getParticipantLimit() != 0) {
                int countRequest = requestRepository.findAllByEventIdAndStatus(eventId, State.CONFIRMED).size();
                if (countRequest >= event.getParticipantLimit()) {
                    throw new ConflictException("у события достигнут лимит запросов на участие");
                }
            }
            request = new Request(null, LocalDateTime.now(), event, user, State.CONFIRMED);
        } else {
            if (event.getParticipantLimit() != 0) {
                request = new Request(null, LocalDateTime.now(), event, user, State.PENDING);
            } else {
                request = new Request(null, LocalDateTime.now(), event, user, State.CONFIRMED);
            }
        }
        requestRepository.save(request);
        return RequestMappers.toDto(request);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestsId) {
        validate.getUserById(userId);
        Request request = validate.getRequestById(requestsId);
        if (!request.getRequester().getId().equals(userId)) {
            throw new EntityNotFoundException("Запрос не найден");
        }
        request.setStatus(State.CANCELED);
        requestRepository.save(request);
        return RequestMappers.toDto(request);
    }
}
