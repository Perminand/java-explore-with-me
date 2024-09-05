package ewm.server.repository;

import ewm.server.model.EndpointHit;
import ewm.server.model.ViewStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
    @Query("select e.app, e.uri, count(e.app) hits from EndpointHit e " +
            "where e.timestamp > :startTime AND e.timestamp < :endTime " +
            "group by e.app, e.uri")
    List<ViewStats> findByTimestampAfterAndTimestampBefore(LocalDateTime startTime, LocalDateTime endTime);
}
