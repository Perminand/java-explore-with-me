package ewm.server.mappers;

import ewm.server.model.ViewStats;
import ru.practicum.ewm.ViewStatsDto;

public class HitViewStatsMapper {
    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return new ViewStatsDto(viewStats.getApp(), viewStats.getUrl(), viewStats.getHits());
    }
}
