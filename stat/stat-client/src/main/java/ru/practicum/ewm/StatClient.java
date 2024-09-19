package ru.practicum.ewm;

import ru.practicum.dto.StatisticDto;
import ru.practicum.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatClient {
    StatisticDto createStat(StatisticDto hit);

    List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, String uris, Boolean unique);

}
