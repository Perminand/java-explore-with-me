package ewm.server.service;

import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.model.dto.stat.ViewStatsDto;
import ewm.server.model.EndpointHit;

import java.util.List;

public interface StatService {
    void create(EndpointHitDto hit);

    List<ViewStatsDto> get(String start, String end, String[] uris, boolean unique);

}
