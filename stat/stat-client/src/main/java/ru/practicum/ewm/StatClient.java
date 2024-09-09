package ru.practicum.ewm;

import java.time.LocalDateTime;
import java.util.List;

public interface StatClient {
    public void createStat(EndpointHitDto hit);

    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
