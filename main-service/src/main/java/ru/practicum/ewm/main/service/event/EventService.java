package ru.practicum.ewm.main.service.event;

import ru.practicum.ewm.main.dto.EventParamsLongDto;
import ru.practicum.ewm.main.dto.EventParamsShortDto;
import ru.practicum.ewm.main.dto.event.EventDto;
import ru.practicum.ewm.main.dto.event.EventFullDto;
import ru.practicum.ewm.main.dto.event.EventShortDto;
import ru.practicum.ewm.main.dto.eventRequest.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main.dto.eventRequest.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.main.dto.request.UpdateEventAdminRequestDto;

import java.util.List;

public interface EventService {
    EventFullDto update(Long eventId, UpdateEventAdminRequestDto updateRequestDto);

    List<EventShortDto> getEventsByUser(Long userId, Integer from, Integer size);

    EventFullDto create(Long userId, EventDto eventDto);

    EventFullDto getEventByUserId(Long userId, Long eventId);

    List<ParticipationRequestDto> requestEventByUserId(Long userId, Long eventId);

    EventFullDto updateEventByUserId(Long userId, Long eventId, UpdateEventAdminRequestDto request);

    EventRequestStatusUpdateResult updateStatusRequestEventByUser(Long userId, Long eventId, EventRequestStatusUpdateRequest result);

    List<EventShortDto> getEventsShort(EventParamsShortDto paramDto);

    EventFullDto getEvent(Long id);

    List<EventFullDto> getEventsFull(EventParamsLongDto paramsDto);
}
