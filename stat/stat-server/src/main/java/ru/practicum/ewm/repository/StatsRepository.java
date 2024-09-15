package ru.practicum.ewm.repository;

import ru.practicum.dto.StatisticDto;
import ru.practicum.dto.StatisticResponse;
import ru.practicum.dto.ViewsStatsRequest;

import java.util.List;

/**
 * Интерфейс для работы с репозиторием статистики.
 */
public interface StatsRepository {
    void saveHit(StatisticDto hit);

    List<StatisticResponse> getStats(ViewsStatsRequest request);

    List<StatisticResponse> getUniqueStats(ViewsStatsRequest request);
}