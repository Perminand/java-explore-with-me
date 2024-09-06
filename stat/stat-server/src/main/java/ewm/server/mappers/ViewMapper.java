package ewm.server.mappers;

import ru.practicum.ewm.model.dto.stat.ViewStatsDto;
import ewm.server.model.EndpointHit;
import ewm.server.model.ViewStats;

public class HitViewStatsMapper {
    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return new ViewStatsDto(viewStats.getApp(), viewStats.getUrl(), viewStats.getHits());
    }
}
