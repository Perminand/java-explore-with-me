package ru.practicum.ewm.service;

import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.ewm.ViewsStatsRequest;

import java.util.List;

public interface StatService {
    void create(EndpointHitDto hit);

    List<ViewStatsDto> getViewStatsList(ViewsStatsRequest build);
}
