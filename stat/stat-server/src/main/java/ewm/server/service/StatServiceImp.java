package ewm.server.service;

import ewm.dto.model.dto.stat.EndpointHitDto;
import ewm.dto.model.dto.stat.ViewStatsDto;
import ewm.server.mappers.EndpointHitMapper;
import ewm.server.mappers.HitViewStatsMapper;
import ewm.server.model.EndpointHit;
import ewm.server.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImp implements StatService {
    private final StatsRepository statsRepository;

    @Override
    public EndpointHitDto create(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = EndpointHitMapper.toEndpointHit(endpointHitDto);
        endpointHit = statsRepository.save(endpointHit);
        EndpointHitDto endpointHitDtoOut = EndpointHitMapper.toEndpointHitDto(endpointHit);
        return endpointHitDtoOut;

    }

    @Override
    public List<ViewStatsDto> get(String start, String end, String[] uris, boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<EndpointHit> result = new ArrayList<>();
        if(uris != null) {
            if (unique) {
                for (String s : uris) {
                }
            } else {
                for (String s : uris) {
                }
            }
        } else {
            result = statsRepository.findByTimestampAfterAndTimestampBefore(startTime, endTime);
        }
        List<ViewStatsDto> viewStatsDtoList = result.stream()
                .map(HitViewStatsMapper::toViewStats)
                .map(HitViewStatsMapper::toViewStatsDto)
                .toList();

        return viewStatsDtoList;
    }
}
