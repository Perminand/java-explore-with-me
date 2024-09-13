package ru.practicum.ewm.main.service.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.mappers.RequestMapper;
import ru.practicum.ewm.main.model.ParticipationRequestDto;
import ru.practicum.ewm.main.model.Request;
import ru.practicum.ewm.main.model.State;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.validate.Validate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService{
    private final Validate validate;
    @Override
    public List<ParticipationRequestDto> getRequestByUserId(Long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto createRequestEventIdByUserId(Long userId, Long eventId) {
        User user = validate.validateUser(userId);
        Event event = validate.validateEvent(eventId);
        Request request = new Request(null, LocalDateTime.now(), event, user, State.PENDING);
    }

    @Override
    public ParticipationRequestDto updaetRequestEventIdByUserId(Long userId, Long requestsId) {
        return null;
    }
}
