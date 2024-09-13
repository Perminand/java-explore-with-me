package ru.practicum.ewm.main.service.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.ewm.main.exceptions.errors.ConflictException;
import ru.practicum.ewm.main.mappers.RequestMapper;
import ru.practicum.ewm.main.model.ParticipationRequestDto;
import ru.practicum.ewm.main.model.Request;
import ru.practicum.ewm.main.model.State;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.repository.RequestRepository;
import ru.practicum.ewm.main.validate.Validate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService{
    private final Validate validate;
    private final RequestRepository requestRepository;
    @Override
    public List<ParticipationRequestDto> getRequestByUserId(Long userId) {
        return null;
    }

    @Override
    @Transactional
    public ParticipationRequestDto createRequestEventIdByUserId(Long userId, Long eventId) {
        List<Request> re = requestRepository.findAllByRequester_IdAndEvent_Id(userId, eventId);
        if (!re.isEmpty()) {
            throw new ConflictException("Есть уже запрос на участие");
        }
        User user = validate.validateUser(userId);
        Event event = validate.validateEvent(eventId);

        if (event.getInitiator() == user) {
            throw new ConflictException("Инициатор не может подать запрос на участие");
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Событие не опубликовано");
        }

        if (requestRepository.findAllByEvent_IdAndStatus(eventId, State.PUBLISHED).size() >= event.getParticipantLimit()) {
            throw new ConflictException("Лимит регистрации закончился");
        }
        Request request;
        if (!event.getRequestModeration()) {
            request = new Request(null, LocalDateTime.now(), event, user, State.PUBLISHED);

        } else {
            request = new Request(null, LocalDateTime.now(), event, user, State.PENDING);
        }
        requestRepository.save(request);
        return RequestMapper.toDto(request);
    }

    @Override
    public ParticipationRequestDto updateRequestEventIdByUserId(Long userId, Long requestsId) {
        return null;
    }
}
