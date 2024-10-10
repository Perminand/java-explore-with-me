package ru.practicum.ewm.main.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class EventParamsShortDto {
    String text;
    List<Long> categories;
    Boolean paid;
    String rangeStart;
    String rangeEnd;
    Boolean onlyAvailable;
    String sort;
    Integer from;
    Integer size;
}
