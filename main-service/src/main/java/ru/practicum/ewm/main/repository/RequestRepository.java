package ru.practicum.ewm.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.model.Request;
import ru.practicum.ewm.main.model.State;

import java.util.Collection;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequester_IdAndEvent_Id(Long userId, Long eventId);

    Collection<Object> findAllByEvent_IdAndStatus(Long eventId, State state);
}
