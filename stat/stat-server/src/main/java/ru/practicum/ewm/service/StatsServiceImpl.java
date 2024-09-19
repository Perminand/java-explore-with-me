package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.StatisticDto;
import ru.practicum.ewm.exceptions.errors.ValidationException;
import ru.practicum.ewm.mappers.StatisticMapper;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.ViewStats;
import ru.practicum.ewm.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatService {
    private final StatsRepository repository;

    @Override
    public EndpointHit create(StatisticDto hitDto) {
        EndpointHit hit = StatisticMapper.toHit(hitDto);
        return repository.save(hit);
    }

    @Override
    public List<ViewStats> getViewStatsList(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (start.isAfter(end)) {
            throw new ValidationException("Неверные параметры start и end");
        }
        if (unique) {
            if (uris != null) {
                return repository.getWithUriUnique(start, end, uris);
            } else {
                return repository.getWithoutUriUnique(start, end);
            }
        } else {
            if (uris != null) {
                return repository.getWithUri(start, end, uris);
            } else {
                return repository.getWithoutUri(start, end);
            }
        }
    }
}
