package ewm.server.repository;

import ewm.server.model.EndpointHit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
//    @Query("select e from EndpointHit e where e.timestamp > :startTime and e.timestamp < :endTime")
//    List<EndpointHit> findByTimestampAfterAndTimestampBefore(LocalDateTime startTime, LocalDateTime endTime);
}
