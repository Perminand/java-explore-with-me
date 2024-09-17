package ru.practicum.ewm.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.model.compilation.CompilationEvent;

import java.util.List;

public interface CompilationsEventRepository extends JpaRepository<CompilationEvent, Long> {
    List<CompilationEvent> findAllByCompilationId(Long id);

    void deleteAllByCompilationId(Long l);

}
