package ewm.server.controllers;

import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.model.dto.stat.ViewStatsDto;
import ewm.server.model.EndpointHit;
import ewm.server.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.InvalidParameterException;
import java.util.List;

@Slf4j
@RestController
@Validated
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
    public void createHit (@Valid @RequestBody EndpointHitDto hit) {
        log.info("POST request to save information.");
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
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end,
            @RequestParam(name = "uris", required = false) String[] uris,
            @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        return statService.get(start, end, uris, unique);
    }
}
