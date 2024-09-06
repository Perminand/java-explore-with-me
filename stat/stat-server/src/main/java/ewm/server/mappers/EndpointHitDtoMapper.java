package ewm.server.mappers;

import ewm.server.model.EndpointHit;
import ru.practicum.ewm.EndpointHitDto;

public class EndpointHitDtoMapper {
    public static EndpointHit toModel(EndpointHitDto hit) {
        return new EndpointHit(hit.getId(), hit.getApp(), hit.getUri(), hit.getIp(), hit.getTimestamp());
    }

    public static EndpointHitDto toDto(EndpointHit hit) {
        return new EndpointHitDto(hit.getId(), hit.getApp(), hit.getUri(), hit.getIp(),hit.getTimestamp());
    }
}
