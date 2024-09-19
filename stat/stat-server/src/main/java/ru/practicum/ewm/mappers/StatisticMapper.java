package ru.practicum.ewm.mappers;

import ru.practicum.dto.StatisticDto;
import ru.practicum.ewm.model.EndpointHit;

public class StatisticMapper {
    public static EndpointHit toHit(StatisticDto dto) {
        return new EndpointHit(null, dto.getApp(), dto.getUri(), dto.getIp(), dto.getTimestamp());
    }
}
