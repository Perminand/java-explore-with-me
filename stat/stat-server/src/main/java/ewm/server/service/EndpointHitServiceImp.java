package ewm.server.service;

import ewm.dto.model.dto.stat.EndpointHitDto;
import ewm.server.mappers.EndpointHitMapper;
import ewm.server.model.EndpointHit;
import ewm.server.repository.EndpointHitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EndpointHitServiceImp implements EndpointHitService {
    private final EndpointHitRepository endpointHitRepository;

    @Override
    public EndpointHitDto create(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = EndpointHitMapper.toEndpointHit(endpointHitDto);
        endpointHit = endpointHitRepository.save(endpointHit);
        EndpointHitDto endpointHitDtoOut = EndpointHitMapper.toEndpointHitDto(endpointHit);
        return endpointHitDtoOut;

    }
}
