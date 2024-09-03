package ewm.server.repository;

import ewm.server.model.EndpointHit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
}
