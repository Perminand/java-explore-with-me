package ru.practicum.ewm.service;

import ru.practicum.ewm.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.StatisticDto;
import ru.practicum.dto.StatisticResponse;
import ru.practicum.dto.ViewsStatsRequest;

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
    public void create(StatisticDto hit) {
        statRepository.saveHit(hit);
    }

    /**
     * Получение статистики просмотров.
     *
     * @param request объект ViewsStatsRequest, содержащий информацию для запроса статистики
     * @return список объектов ViewStats, содержащих статистику просмотров
     */
    @Override
    public List<StatisticResponse> getViewStatsList(ViewsStatsRequest request) {
        if (request.isUnique()) {
            return statRepository.getUniqueStats(request);
        }
        return statRepository.getStats(request);
    }
}