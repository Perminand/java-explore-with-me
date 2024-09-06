package ru.practicum.ewm;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.ewm.model.dto.stat.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatClientImp implements StatClient {

    private final String statUrl;

    private final WebClient webClient;

    @Autowired
    public StatClientImp(@Value("${stat-server.url}") String statUrl) {
        this.statUrl = statUrl;
        this.webClient = WebClient.create(statUrl);
    }

    public void createStat(EndpointHitDto hit){
        webClient.post()
                .uri("/hit")
                .retrieve();

    }
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return webClient.get()
                .uri("/stats")
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }
}
