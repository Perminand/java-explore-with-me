package ru.practicum.ewm.main.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.event.dto.EventFullDto;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
//    @Query(value = "select e from events AS e")
//    List<Event> getAllForFilter(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Pageable pageable);
}
