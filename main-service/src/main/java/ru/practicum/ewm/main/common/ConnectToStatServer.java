package ru.practicum.ewm.main.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.StatClientImp;
import ru.practicum.dto.StatisticResponse;
import ru.practicum.ewm.main.exceptions.errors.ClientException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ConnectToStatServer {

    public static List<Long> getViews(LocalDateTime start, LocalDateTime end, String uris, boolean unique,
                                      StatClientImp statisticClient) {
        Mono<List<StatisticResponse>> response = statisticClient.getStats(start, end, uris, unique);
        List<StatisticResponse> statisticResponseList = response.block();

        return statisticResponseList
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
