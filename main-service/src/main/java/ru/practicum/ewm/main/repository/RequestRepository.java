package ru.practicum.ewm.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.model.ParticipationRequestDto;
import ru.practicum.ewm.main.model.Request;
import ru.practicum.ewm.main.model.State;
import ru.practicum.ewm.main.model.event.dto.EventIdByRequestsCount;

import java.util.Collection;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequester_IdAndEvent_Id(Long userId, Long eventId);

    Collection<Object> findAllByEvent_IdAndStatus(Long eventId, State state);

    @Query(value = "select count(id), event " +
            "from requests " +
            "where event IN ?1 " +
            "AND status LIKE ?2 " +
            "group by event ", nativeQuery = true)
    List<EventIdByRequestsCount> countByEventIdInAndStatusGroupByEvent(List<Long> eventsIds, String s);

    @Query(value = "Select * from requests r join events e On r.event=e.event_id where r.users=?1 and e.initiator<>?1", nativeQuery = true)
    List<Request> findByRequesterIdAndNotInitiatorId(Long userId);
}
