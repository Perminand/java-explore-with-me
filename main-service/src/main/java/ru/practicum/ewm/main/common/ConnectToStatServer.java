package ru.practicum.ewm.main.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.dto.StatsDto;
import ru.practicum.ewm.StatsClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ConnectToStatServer {
    public static List<Long> getViews(LocalDateTime start, LocalDateTime end, String uris, boolean unique,
                                      StatsClient statisticClient) {
        List<StatsDto> response = statisticClient.getStats(start, end, uris, unique);
        log.info(response.toString());
        return response
                .stream()
                .map(StatsDto::getHits)
                .collect(Collectors.toList());
    }

    public static String prepareUris(List<Long> ids) {
        return ids
                .stream()
                .map((id) -> "event/" + id).collect(Collectors.joining());
    }
}
