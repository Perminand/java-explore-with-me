package ru.practicum.ewm.main.mappers;

import ru.practicum.GeneralConstants;
import ru.practicum.ewm.main.model.request.Request;
import ru.practicum.ewm.main.model.request.dto.ParticipationRequestDto;

public class RequestMappers {
    public static ParticipationRequestDto toDto(Request request) {
        return new ParticipationRequestDto(
                request.getId(),
                request.getCreated().format(GeneralConstants.DATE_FORMATTER),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }
}
