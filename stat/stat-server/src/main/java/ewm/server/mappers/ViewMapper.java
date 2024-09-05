package ewm.server.mappers;

import ewm.dto.model.dto.stat.ViewStatsDto;
import ewm.server.model.EndpointHit;
import ewm.server.model.ViewStats;

public class ViewMapper {
    public static ViewStats toViewStats(EndpointHit endpointHit) {
        return new ViewStats(endpointHit.getApp(), endpointHit.getUri());
    }

    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return new ViewStatsDto(viewStats.getApp(), viewStats.getUrl(), viewStats.getHits());
    }
}
