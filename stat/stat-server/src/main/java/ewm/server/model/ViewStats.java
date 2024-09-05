package ewm.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ViewStats {
    private String app;
    private String url;
    private long hits;

    public ViewStats(String app, String url) {
        this.app = app;
        this.url = url;
    }
}
