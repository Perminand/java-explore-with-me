package ru.practicum.ewm.main.controllers.publicApi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.StatisticDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.ewm.StatClientImp;
import ru.practicum.ewm.main.common.GeneralConstants;
import ru.practicum.ewm.main.model.event.dto.EventFullDto;
import ru.practicum.ewm.main.model.event.dto.EventShortDto;
import ru.practicum.ewm.main.service.event.EventService;
import ru.practicum.ewm.main.validate.Validate;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class EventPublicController {
    private final EventService eventService;
    private final StatClientImp statisticClient;
    private final Validate validate;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventsFilter(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(defaultValue = "false", required = false) Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @Min(0) @RequestParam(defaultValue = "0") Integer from,
            @Min(0) @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest httpServletRequest) {

        String ip = httpServletRequest.getRemoteAddr();
        String path = httpServletRequest.getRequestURI();

        log.info("Get запрос на получение событий с возможностью фильтрации. Поисковые параметры: text: {}, categories: {}, paid: {}," +
                        "rangeStart: {}, rangeEnd: {}, onlyAvailable: {}, sort: {}, from: {}, size: {}", text, categories,
                paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        log.info("Регистрация IP: {}, path: {}", ip, path);

        StatisticDto statisticDto = prepareStatisticDto("ewm-main-service", path, ip);
        statisticClient.createStat(statisticDto);
        log.info("EventPublicController, getEventsFilter. Запрос был отправлен на сервер статистики. Подробнее: {}",
                statisticDto);

        return eventService.getEventsFilter(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from,
                size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEvent(@PathVariable @Min(0) Long id, HttpServletRequest httpServletRequest) {
        log.info("Get запрос на получение события по его идентификатору");
        String ip = httpServletRequest.getRemoteAddr();
        String path = httpServletRequest.getRequestURI();

        log.info("Регистрация IP: {}, path: {}", ip, path);

        StatisticDto statisticDto = prepareStatisticDto("ewm-main-service", path, ip);
        statisticClient.createStat(statisticDto);
        log.info("EventPublicController, getEventsFilter. Запрос был отправлен на сервер статистики. Подробнее: {}",
                statisticDto);
        EventFullDto eventFullDto = eventService.getEvent(id);
        List<StatsDto> statsDtoList = statisticClient.getStats(
                GeneralConstants.defaultStartTime,
                GeneralConstants.defaultEndTime,
                path, false);
        eventFullDto.setViews(Long.valueOf(statsDtoList.size()));
        return eventFullDto;
    }

    private StatisticDto prepareStatisticDto(String app, String uri, String ip) {
        return StatisticDto
                .builder()
                .app(app)
                .uri(uri)
                .ip(ip)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
