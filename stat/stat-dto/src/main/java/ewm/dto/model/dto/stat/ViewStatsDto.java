package ewm.dto.model.dto.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ViewStatsDto {
    private String app;
    private String url;
    private int hits;
}
