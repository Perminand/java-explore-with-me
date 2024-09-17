package ru.practicum.ewm.main.controllers.privateApi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.model.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.model.UpdateEventAdminRequest;
import ru.practicum.ewm.main.model.ParticipationRequestDto;
import ru.practicum.ewm.main.model.event.dto.EventDto;
import ru.practicum.ewm.main.model.event.dto.EventFullDto;
import ru.practicum.ewm.main.model.event.dto.EventShortDto;
import ru.practicum.ewm.main.service.event.EventService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class EventPrivateController {
    private final EventService eventService;


    @GetMapping("{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventsByUser(
            @PathVariable @Min(0) Long userId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET запрос на получение событий добавленных пользователем");
        return eventService.getEventsByUser(userId, from, size);
    }

    @PostMapping("{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(
            @PathVariable @Min(0) Long userId,
            @RequestBody @Valid EventDto eventDto) {
        log.info("POST запрос на сохранение события");
        return eventService.create(userId, eventDto);
    }

    @GetMapping("{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventByUserId(
            @PathVariable @Min(0) Long userId,
            @PathVariable @Min(0) Long eventId) {
        log.info("Get запрос на получение полной информации о событии текущего пользователя");
        return eventService.getEventByUserId(userId, eventId);
    }

    @GetMapping("{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> requestEventByUserId(
            @PathVariable @Min(0) Long userId,
            @PathVariable @Min(0) Long eventId) {
        log.info("Get запрос на получение информации о запросах на участие в событии текущего пользователя");
        return eventService.requestEventByUserId(userId, eventId);
    }

    @PatchMapping("{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventByUserId(
            @PathVariable @Min(0) Long userId,
            @PathVariable @Min(0) Long eventId,
            @RequestBody @Valid UpdateEventAdminRequest request) {
        log.info("Patch запрос на изменение события добавленного текущим пользователем");
        return eventService.updateEventByUserId(userId, eventId, request);
    }

    @PatchMapping("{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateStatusRequestEventByUser(
            @PathVariable @Min(0) Long userId,
            @PathVariable @Min(0) Long eventId,
            @RequestBody @Valid EventRequestStatusUpdateResult result) {
        log.info("Patch запрос на изменение статуса заявок на участие в событии текущего пользователя");
        return eventService.updateStatusRequestEventByUser(userId, eventId, result);
    }

}
