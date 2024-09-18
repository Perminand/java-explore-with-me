package ru.practicum.ewm.main.service.requests;

import ru.practicum.ewm.main.model.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getRequestByUserId(Long userId);

    ParticipationRequestDto createRequestEventIdByUserId(Long userId, Long eventId);

    ParticipationRequestDto cancelRequestEventIdByUserId(Long userId, Long requestsId);
}
