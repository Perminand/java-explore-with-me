package ru.practicum.ewm.main.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.model.ParticipationRequestDto;
import ru.practicum.ewm.main.model.Request;
import ru.practicum.ewm.main.model.State;
import ru.practicum.ewm.main.model.event.dto.EventIdByRequestsCount;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequester_IdAndEvent_Id(Long userId, Long eventId);

    Collection<Object> findAllByEvent_IdAndStatus(Long eventId, State state);

    List<ParticipationRequestDto> findByRequester_Id(Long userId, Pageable pageable);

    @Query(value = "select count(id), event " +
            "from requests " +
            "where event IN ?1 " +
            "AND status LIKE ?2 " +
            "group by event ", nativeQuery = true)
    List<EventIdByRequestsCount> countByEventIdInAndStatusGroupByEvent(List<Long> eventsIds, String s);
}
