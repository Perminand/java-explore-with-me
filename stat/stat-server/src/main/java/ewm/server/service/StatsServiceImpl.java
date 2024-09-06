package ewm.server.service;

import ewm.server.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewsStatsRequest;
import ru.practicum.ewm.model.dto.stat.ViewStatsDto;


import java.util.List;

/**
 * Реализация интерфейса StatsService.
 */
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatService {
    private final StatsRepository statRepository;

    /**
     * Сохранение информации о попадании на эндпоинт
     *
     * @param hit объект EndpointHit, содержащий информацию о попадании на эндпоинт
     */
    @Override
    public void create(EndpointHitDto hit) {
        statRepository.saveHit(hit);
    }

    /**
     * Получение статистики просмотров.
     *
     * @param request объект ViewsStatsRequest, содержащий информацию для запроса статистики
     * @return список объектов ViewStats, содержащих статистику просмотров
     */
    @Override
    public List<ViewStatsDto> getViewStatsList(ViewsStatsRequest request) {
        if (request.isUnique()) {
            return statRepository.getUniqueStats(request);
        }
        return statRepository.getStats(request);
    }
}