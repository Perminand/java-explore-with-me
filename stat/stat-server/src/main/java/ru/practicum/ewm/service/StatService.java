package ru.practicum.ewm.service;

import ru.practicum.dto.StatisticDto;
import ru.practicum.dto.StatisticResponse;
import ru.practicum.dto.ViewsStatsRequest;

import java.util.List;

public interface StatService {
    void create(StatisticDto hit);

    List<StatisticResponse> getViewStatsList(ViewsStatsRequest build);
}
