package ru.practicum.ewm.main.mappers;

import ru.practicum.ewm.main.model.ParticipationRequestDto;
import ru.practicum.ewm.main.model.Request;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.users.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestMappers {
    public static Request toRequest(ParticipationRequestDto requestDto, Event event, User user) {
        return new Request(
                requestDto.getId(),
                LocalDateTime.parse(requestDto.getCreated(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                event,
                user,
                requestDto.getStatus());

    }

    public static ParticipationRequestDto toDto(Request request) {
        return new ParticipationRequestDto(
                request.getId(),
                request.getCreated().toString(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }
}
