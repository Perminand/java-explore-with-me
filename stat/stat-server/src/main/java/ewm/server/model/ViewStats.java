package ewm.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ViewStats {
    private String app;
    private String url;
    private int hits;
}
