package ewm.server.service;

import ewm.server.mappers.EndpointHitDtoMapper;
import ewm.server.mappers.HitViewStatsMapper;
import ewm.server.model.EndpointHit;
import ewm.server.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewsStatsRequest;
import ru.practicum.ewm.model.dto.stat.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImp implements StatService {
    private final StatsRepository statsRepository;

    @Override
    public void create(EndpointHitDto hit) {
        statsRepository.save(EndpointHitDtoMapper.toModel(hit));
    }

    @Override
    public List<ViewStatsDto> getViewStatsList(ViewsStatsRequest request) {
        if (!request.isUnique()) {
            if (!request.getUris().isEmpty()) {
                return statsRepository.selectAllWhereCreatedAfterStartAndBeforeEndUris(
                        request.getStart(),
                        request.getEnd(),
                        request.getUris());

            } else {
                return statsRepository.selectAllWhereCreatedAfterStartAndBeforeEnd(request.getStart(), request.getEnd());
            }
        }
        return null;
//        return statsRepository.selectAllHitsWhereCreatedAfterStartAndBeforeEndInUris(request.getStart(), request.getEnd());
    }
}
