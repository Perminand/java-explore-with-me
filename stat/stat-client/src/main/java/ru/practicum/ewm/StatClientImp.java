package ru.practicum.ewm;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
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
    public Mono<ResponseEntity<StatisticDto>> createStat(StatisticDto hit) {
        return webClient.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(hit, StatisticDto.class)
                .retrieve()
                .toEntity(StatisticDto.class);
    }
    @Override
    public Mono<List<StatisticResponse>> getStats(LocalDateTime start, LocalDateTime end, String uris, Boolean unique) {
        Mono<List<StatisticResponse>> mono =  webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/stats")
                        .queryParam("start", start)
                        .queryParam("end", end)
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new RuntimeException("Апи не найдено")))
                .onStatus(HttpStatusCode::is5xxServerError, error -> Mono.error(new RuntimeException("Сервер не может обработать запрос")))
                .bodyToMono(new ParameterizedTypeReference<List<StatisticResponse>>() {});
        return mono;
    }
}
