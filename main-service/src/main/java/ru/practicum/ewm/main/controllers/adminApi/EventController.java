package ru.practicum.ewm.main.controllers.adminApi;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.model.UpdateEventAdminRequest;
import ru.practicum.ewm.main.model.dto.event.EventFullDto;
import ru.practicum.ewm.main.service.event.EventService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class EventController {
    private final EventService eventService;


    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEvent(
            @RequestParam List<Long> users,
            @RequestParam List<String> states,
            @RequestParam List<Long> categories,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get запрос на получение списка событий");
        return eventService.get(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/users/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEvent(@Min(0) @PathVariable Long eventId, @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Patch запрос на изменение события");
        eventService.update(eventId, updateEventAdminRequest);
    }
}
