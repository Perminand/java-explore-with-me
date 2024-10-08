package ru.practicum.ewm.main.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.event.EventStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * " +
            "FROM events AS e " +
            "WHERE ((e.initiator IN (?1) OR ?1 IS NULL) " +
            "AND (e.category IN (?2) OR ?2 IS NULL) " +
            "AND (e.state IN (?3) OR ?3 IS NULL) " +
            "AND (e.event_date BETWEEN ?4 AND ?5)) ", nativeQuery = true)
    List<Event> findAllByInitiatorIdInAndCategoryIdInAndStateInAndDateTimeBetween(
            List<Long> initiator,
            List<Long> category,
            List<String> state,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable);

    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM events As e " +
            "WHERE (e.annotation ILIKE (?1) OR e.description ILIKE (?1%) OR ?1 IS NULL) " +
            "AND (e.category IN (?2) OR ?2 IS NULL) " +
            "AND (e.paid = CAST(?3 AS boolean) OR ?3 IS NULL) " +
            "AND (e.event_date BETWEEN ?4 AND ?5) " +
            "AND (CAST(?6 AS BOOLEAN) is TRUE OR(select count(id) from requests AS r WHERE r.event = e.event_id) < participants_limit) " +
            "AND (state = 'PUBLISHED')", nativeQuery = true)
    List<Event> searchEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable pageable
    );

    Event findByIdAndState(Long id, EventStatus state);

    Event findByIdAndInitiatorId(Long eventId, Long userId);

    Optional<Event> findByCategoryId(Long catId);
}
