package ewm.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StatClient {
    final RestTemplate restTemplate;
    final String statUrl;

    public StatClient(RestTemplate restTemplate, @Value("${client.url}") String statUrl) {
        this.restTemplate = restTemplate;
        this.statUrl = statUrl;
    }
}
