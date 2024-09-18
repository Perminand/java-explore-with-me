package ru.practicum.ewm.main.controllers.adminApi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.model.event.dto.EventFullDto;
import ru.practicum.ewm.main.model.request.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.main.service.event.EventService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class EventAdminController {
    private final EventService eventService;

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEvents(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Get запрос на получение списка событий");
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(@Min(0) @PathVariable Long eventId, @RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Patch запрос на изменение события");
        return eventService.update(eventId, updateEventAdminRequest);
    }
}
