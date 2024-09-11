package ru.practicum.ewm.main.service.event;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.model.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.model.UpdateEventAdminRequest;
import ru.practicum.ewm.main.model.dto.ParticipationRequestDto;
import ru.practicum.ewm.main.model.dto.event.EventDto;
import ru.practicum.ewm.main.model.dto.event.EventFullDto;
import ru.practicum.ewm.main.model.dto.event.EventShortDto;

import java.util.List;

@Service
public class EventServiceImpl implements EventService{
    @Override
    public List<EventFullDto> get(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
        return null;
    }

    @Override
    public void update(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {

    }

    @Override
    public List<EventShortDto> getEventsByUser(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto create(Long userId, EventDto eventDto) {
        return null;
    }

    @Override
    public EventFullDto getEventByUserId(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto requestEventByUserId(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto updateEventByUserId(Long userId, Long eventId, UpdateEventAdminRequest request) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateStatusRequestEventByUser(Long userId, Long eventId, EventRequestStatusUpdateResult result) {
        return null;
    }

    @Override
    public List<EventShortDto> getEventsFilter(String text, List<Long> categories, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto getEvent(Long id) {
        return null;
    }
}
