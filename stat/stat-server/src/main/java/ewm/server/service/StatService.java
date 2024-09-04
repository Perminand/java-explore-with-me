package ewm.server.service;

import ewm.dto.model.dto.stat.EndpointHitDto;
import ewm.dto.model.dto.stat.ViewStatsDto;

import java.util.List;

public interface StatService {
    EndpointHitDto create(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> get(String start, String end, String[] uris, boolean unique);

}
