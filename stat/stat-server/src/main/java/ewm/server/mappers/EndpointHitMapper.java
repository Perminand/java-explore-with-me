package ewm.server.mappers;

import ewm.dto.model.dto.stat.EndpointHitDto;
import ewm.server.model.EndpointHit;

public class EndpointHitMapper {

    public static EndpointHit toEndpointHit (EndpointHitDto e) {
        return new EndpointHit(e.getId(), e.getApp(), e.getUrl(), e.getIp(), e.getTimeStamp());
    }

    public static EndpointHitDto toEndpointHitDto(EndpointHit e) {
        return  new EndpointHitDto(e.getId(), e.getApp(), e.getUrl(), e.getIp(), e.getTimeStamp());
    }
}
