package ru.practicum.ewm.main.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.StatClientImp;
import ru.practicum.dto.StatisticResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ConnectToStatServer {

    public static List<Long> getViews(LocalDateTime start, LocalDateTime end, String uris, boolean unique,
                                      StatClientImp statisticClient) {
        List<StatisticResponse> response = statisticClient.getStats(start, end, uris, unique);

        return response
                .stream()
                .map(StatisticResponse::getHits)
                .collect(Collectors.toList());
    }

    public static String prepareUris(List<Long> ids) {
        return ids
                .stream()
                .map((id) -> "event/" + id).collect(Collectors.joining());
    }
}
