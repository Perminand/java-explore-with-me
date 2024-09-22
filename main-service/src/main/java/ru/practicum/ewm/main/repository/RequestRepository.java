package ru.practicum.ewm.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.dto.event.EventIdByRequestsCount;
import ru.practicum.ewm.main.model.request.Request;
import ru.practicum.ewm.main.model.request.RequestStatus;

import java.util.Collection;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterIdAndEventId(Long userId, Long eventId);

    Collection<Object> findAllByEventIdAndStatus(Long eventId, RequestStatus state);

    @Query(value = "select count(id), event " +
            "from requests " +
            "where event IN ?1 " +
            "AND status LIKE ?2 " +
            "group by event ", nativeQuery = true)
    List<EventIdByRequestsCount> countByEventIdInAndStatusGroupByEvent(List<Long> eventsIds, String s);

    @Query(value = "Select * from requests r join events e On r.event=e.event_id where r.users=?1 and e.initiator<>?1", nativeQuery = true)
    List<Request> findByRequesterIdAndNotInitiatorId(Long userId);


    @Query(value = "SELECT * FROM requests r join events e ON r.event=e.event_id where r.event=?1 AND e.initiator=?2", nativeQuery = true)
    List<Request> findAllByEventIdAndInitiatorId(Long eventId, Long userId);

    Long countByEventIdAndStatus(Long eventId, RequestStatus state);

    List<Request> findAllByEventIdInAndStatus(List<Long> requestIds, RequestStatus state);

    List<Request> findAllByIdInAndStatus(List<Long> requestIds, RequestStatus state);
}
