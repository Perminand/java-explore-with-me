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

    }
