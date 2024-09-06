package ewm.server.service;

import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewsStatsRequest;
import ru.practicum.ewm.model.dto.stat.ViewStatsDto;

import java.util.List;

public interface StatService {
    void create(EndpointHitDto hit);

    List<ViewStatsDto> getViewStatsList(ViewsStatsRequest build);
}
