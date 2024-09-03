package ewm.dto.model.dto.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EndpointHitDto {
    private long id;
    private String app;
    private String uri;
    private String ip;
    private String timeStamp;

    public EndpointHitDto(String app, String url, String ip, String timeStamp) {
        this.app = app;
        this.url = url;
        this.ip = ip;
        this.timeStamp = timeStamp;
    }
}
