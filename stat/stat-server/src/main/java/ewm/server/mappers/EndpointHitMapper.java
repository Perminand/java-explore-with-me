package ewm.server.mappers;

import ewm.dto.model.dto.stat.EndpointHitDto;
import ewm.server.model.EndpointHit;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EndpointHitMapper {

    public static EndpointHit toEndpointHit (EndpointHitDto e) {
        LocalDateTime localDateTime = LocalDateTime.parse(e.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return new EndpointHit(e.getId(), e.getApp(), e.getUri(), e.getIp(), localDateTime);
    }

    public static EndpointHitDto toEndpointHitDto(EndpointHit e) {
        return  new EndpointHitDto(e.getId(), e.getApp(), e.getUri(), e.getIp(), e.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
