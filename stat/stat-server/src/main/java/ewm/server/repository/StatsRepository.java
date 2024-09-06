package ewm.server.repository;

import ewm.server.model.EndpointHit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.dto.stat.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT app, uri, COUNT (ip) AS hits FROM EndpointHit " +
            "WHERE (timestamp >= :start AND timestamp <= :end) " +
            "GROUP BY app, uri ORDER BY hits DESC")
    List<ViewStatsDto> selectAllWhereCreatedAfterStartAndBeforeEnd(LocalDateTime start, LocalDateTime end);

    @Query("SELECT app, uri, COUNT (ip) AS hits FROM EndpointHit " +
            "WHERE (timestamp >= :start AND timestamp <= :end AND uri IN :uris) " +
            "GROUP BY app, uri ORDER BY hits DESC")
    List<ViewStatsDto> selectAllWhereCreatedAfterStartAndBeforeEndUris(LocalDateTime start, LocalDateTime end, List<String> uris);
}
