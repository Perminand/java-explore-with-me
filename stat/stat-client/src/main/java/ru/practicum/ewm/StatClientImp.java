package ru.practicum.ewm;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.GeneralConstants;
import ru.practicum.dto.StatisticDto;
import ru.practicum.dto.StatisticResponse;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatClientImp implements StatClient {

    private final WebClient webClient;

    @Autowired
    public StatClientImp(@Value("${stat-server.url}") String statUrl) {
        this.webClient = WebClient.create(statUrl);
    }

    @Override
    public StatisticDto createStat(StatisticDto hit) {
        return webClient.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(hit))
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is5xxServerError()) {
                        return Mono.error(new RuntimeException("Ошибка клиента"));
                    } else if (clientResponse.statusCode().is4xxClientError()) {
                        return Mono.error(new RuntimeException("Ошибка клиента"));
                    } else {
                        return clientResponse.bodyToMono(StatisticDto.class);
                    }
                })
                .block();
    }

    @Override
    public List<StatisticResponse> getStats(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime, String uris, Boolean unique) {
        String start = startLocalDateTime.format(GeneralConstants.DATE_FORMATTER);
        String end = endLocalDateTime.format(GeneralConstants.DATE_FORMATTER);
        String uri;
        if (uris != null) {
            uri = "/stats?start={start}&end={end}&uris={urisString}&unique={unique}";
        } else {
            uri = "/stats?start={start}&end={end}&unique={unique}";
        }
        return webClient
                .get()
                .uri(uri, start, end, uris, String.valueOf(unique))
                .exchangeToFlux(clientResponse -> {
                    if (clientResponse.statusCode().is5xxServerError()) {
                        return Flux.error(new RuntimeException("Server Error"));
                    } else if (clientResponse.statusCode().is4xxClientError()) {
                        return Flux.error(new RuntimeException("Client Error"));
                    } else {
                        return clientResponse.bodyToFlux(StatisticResponse.class);
                    }
                })
                .collectList()
                .block();
    }
}
