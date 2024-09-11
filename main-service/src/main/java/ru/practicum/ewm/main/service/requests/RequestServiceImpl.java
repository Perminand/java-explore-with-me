package ru.practicum.ewm.main.service.requests;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.model.dto.ParticipationRequestDto;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService{
    @Override
    public List<ParticipationRequestDto> getRequestByUserId(Long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto createRequestEventIdByUserId(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto updaetRequestEventIdByUserId(Long userId, Long requestsId) {
        return null;
    }
}
