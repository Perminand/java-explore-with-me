package ru.practicum.ewm.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
