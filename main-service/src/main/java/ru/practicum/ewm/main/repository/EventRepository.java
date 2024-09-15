package ru.practicum.ewm.main.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.model.event.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * " +
            "FROM events AS e " +
            "WHERE ((e.initiator IN (?1) OR ?1 IS NULL) " +
            "AND (e.category IN (?2) OR ?2 IS NULL) " +
            "AND (e.state IN (?3) OR ?3 IS NULL) " +
            "AND (e.event_date BETWEEN ?4 AND ?5)) ", nativeQuery = true)
    List<Event> findAllByInitiatorIdInAndStateInAndCategoryIdInAndDateTimeBetween(
            List<Long> initiator,
            List<Long> category,
            List<String> state,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable);

}
