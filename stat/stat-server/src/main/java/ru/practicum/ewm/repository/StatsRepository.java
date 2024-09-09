package ru.practicum.ewm.repository;

import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.ewm.ViewsStatsRequest;

import java.util.List;

/**
 * Интерфейс для работы с репозиторием статистики.
 */
public interface StatsRepository {
    void saveHit(EndpointHitDto hit);

    List<ViewStatsDto> getStats(ViewsStatsRequest request);

    List<ViewStatsDto> getUniqueStats(ViewsStatsRequest request);
}