package ru.practicum.ewm.service;

import ru.practicum.dto.StatisticDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.ewm.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    Hit create(StatisticDto hit);

    List<StatsDto> getViewStatsList(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
