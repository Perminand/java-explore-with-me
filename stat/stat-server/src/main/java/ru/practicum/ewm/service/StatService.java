package ru.practicum.ewm.service;

import ru.practicum.dto.StatisticDto;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    EndpointHit create(StatisticDto hit);

    List<ViewStats> getViewStatsList(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
