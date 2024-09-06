package ewm.server.controllers;

import ewm.server.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewsStatsRequest;
import ru.practicum.ewm.model.dto.stat.ViewStatsDto;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    /**
     * Обработка POST-запроса на сохранение информации о хите на эндпоинт.
     *
     * @param hit объект EndpointHit, содержащий информацию о хите
     */

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated
    public void createHit (@RequestBody EndpointHitDto hit) {
        log.info("POST запрос на сохранение информации.");
        statService.create(hit);
    }

    /**
     * Обработка GET-запроса на получение статистики просмотров.
     *
     * @param start  начальная дата и время периода статистики
     * @param end    конечная дата и время периода статистики
     * @param uris   список URI для фильтрации статистики
     * @param unique флаг, указывающий нужно ли получить уникальные просмотры
     * @return список объектов ViewStats, содержащих статистику просмотров
     * @throws InvalidParameterException если переданы некорректные параметры запроса
     */
    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStatsDto> get(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(defaultValue = "") List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique) {
        log.info("GET запрос на получение всей статистики.");
        if(end.isBefore(start)) {
            log.info("Некорректный формат start {} и end {}", start, end);
            throw new InvalidParameterException("Некорректный формат дат");
        }
        return statService.getViewStatsList(
                ViewsStatsRequest.builder()
                        .start(start)
                        .end(end)
                        .uris(uris)
                        .unique(unique)
                        .build());
    }
}
