package ru.practicum.ewm;

import reactor.core.publisher.Mono;
import ru.practicum.dto.StatisticDto;
import ru.practicum.dto.StatisticResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface StatClient {
    public void createStat(StatisticDto hit);

    Mono<List<StatisticResponse>> getStats(LocalDateTime start, LocalDateTime end, String uris, Boolean unique);
}
