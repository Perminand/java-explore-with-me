package ewm.server.service;

import ewm.dto.model.dto.stat.EndpointHitDto;
import ewm.dto.model.dto.stat.ViewStatsDto;

public interface StatService {
    EndpointHitDto create(EndpointHitDto endpointHitDto);

    ViewStatsDto get(String start, String end, String[] uris, boolean unique);

}
