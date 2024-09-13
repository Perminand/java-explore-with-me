package ru.practicum.ewm.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.model.event.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
