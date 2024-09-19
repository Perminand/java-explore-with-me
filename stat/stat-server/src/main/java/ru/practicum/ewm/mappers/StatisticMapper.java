package ru.practicum.ewm.mappers;

import ru.practicum.dto.StatisticDto;
import ru.practicum.ewm.model.Hit;

public class StatisticMapper {
    public static Hit toHit(StatisticDto dto) {
        return new Hit(null, dto.getApp(), dto.getUri(), dto.getIp(), dto.getTimestamp());
    }
}
