package ru.practicum.ewm.main.controllers.privateApi;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.model.ParticipationRequestDto;
import ru.practicum.ewm.main.service.requests.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class RequestPrivateController {
    private final RequestService requestService;


    @GetMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequestByUserId(
            @PathVariable @Min(0) Long userId) {
        log.info("Get запрос на получение запросов на участие в мероприятиях других пользователей");
        return requestService.getRequestByUserId(userId);
    }

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequestEventIdByUserId(
            @PathVariable @Min(0) Long userId,
            @RequestParam @Min(0) Long eventId) {
        log.info("Post запрос от текущего на участие в событии ");
        return requestService.createRequestEventIdByUserId(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestsId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto updateRequestEventIdByUserId(
            @PathVariable @Min(0) Long userId,
            @PathVariable @Min(0) Long requestsId) {
        log.info("Patch запрос на отмену участия в событии");
        return requestService.updateRequestEventIdByUserId(userId, requestsId);
    }
}
