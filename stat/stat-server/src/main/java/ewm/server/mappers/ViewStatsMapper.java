package ewm.server.mappers;

import ewm.dto.model.dto.stat.ViewStatsDto;
import ewm.server.model.ViewStats;

public class ViewStatsMapper {
    public static ViewStats toViewStats(ViewStatsDto viewStatsDto) {
        return new ViewStats(viewStatsDto.getApp(),viewStatsDto.getUrl(), viewStatsDto.getHits());
    }

    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return new ViewStatsDto(viewStats.getApp(), viewStats.getUrl(), viewStats.getHits());
    }
}
