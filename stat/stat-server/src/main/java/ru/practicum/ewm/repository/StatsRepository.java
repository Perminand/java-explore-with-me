package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query(value = "select new ru.practicum.EndpointHit" +
            "(s.app, s.uri, count(s.uri)) from Hit as s " +
            "where s.timestamp between ?1 and ?2 " +
            "and s.uri in ?3 " +
            "group by s.app, s.uri " +
            "order by count(s.uri) desc ")
    List<ViewStats> getWithUri(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "select new ru.practicum.EndpointHit" +
            "(s.app, s.uri, count(distinct s.ip)) from Hit as s " +
            "where s.timestamp between ?1 and ?2 " +
            "and s.uri in ?3 " +
            "group by s.app, s.uri " +
            "order by count(distinct s.ip) desc")
    List<ViewStats> getWithUriUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "select new ru.practicum.EndpointHit" +
            "(s.app, s.uri, count(s.uri)) from Hit as s " +
            "where s.timestamp between ?1 and ?2 " +
            "group by s.app, s.uri " +
            "order by count(s.uri) desc ")
    List<ViewStats> getWithoutUri(LocalDateTime start, LocalDateTime end);

    @Query(value = "select new ru.practicum.EndpointHit" +
            "(s.app, s.uri, count(distinct s.ip)) from Hit as s " +
            "where s.timestamp between ?1 and ?2 " +
            "group by s.app, s.uri " +
            "order by count(distinct s.ip) desc")
    List<ViewStats> getWithoutUriUnique(LocalDateTime start, LocalDateTime end);
}
