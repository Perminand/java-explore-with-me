package ewm.server.service;

import ewm.dto.model.dto.stat.EndpointHitDto;
import ewm.dto.model.dto.stat.ViewStatsDto;
import ewm.server.mappers.EndpointHitMapper;
import ewm.server.model.EndpointHit;
import ewm.server.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public ViewStatsDto get(String start, String end, String[] uris, boolean unique) {
        return null;
    }
}
