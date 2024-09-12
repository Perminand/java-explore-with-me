package ru.practicum.ewm.main.service.event;

import ru.practicum.ewm.main.model.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.model.UpdateEventAdminRequest;
import ru.practicum.ewm.main.model.ParticipationRequestDto;
import ru.practicum.ewm.main.model.event.EventDto;
import ru.practicum.ewm.main.model.event.EventFullDto;
import ru.practicum.ewm.main.model.event.EventShortDto;

import java.util.List;

public interface EventService {
    List<EventFullDto> get(
            List<Long> users,
            List<String> states,
            List<Long> categories,
            String rangeStart,
            String rangeEnd,
            Integer from,
            Integer size
    );

    void update(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    List<EventShortDto> getEventsByUser(Long userId, Integer from, Integer size);

    EventFullDto create(Long userId, EventDto eventDto);

    EventFullDto getEventByUserId(Long userId, Long eventId);

    ParticipationRequestDto requestEventByUserId(Long userId, Long eventId);

    EventFullDto updateEventByUserId(Long userId, Long eventId, UpdateEventAdminRequest request);

    EventRequestStatusUpdateResult updateStatusRequestEventByUser(Long userId, Long eventId, EventRequestStatusUpdateResult result);

    List<EventShortDto> getEventsFilter(String text, List<Long> categories, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size);

    EventFullDto getEvent(Long id);
}
