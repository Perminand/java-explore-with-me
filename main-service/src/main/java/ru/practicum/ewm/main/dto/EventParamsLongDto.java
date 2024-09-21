package ru.practicum.ewm.main.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class EventParamsLongDto {
    List<Long> initiator;
    List<String> state;
    List<Long> category;
    String rangeStart;
    String rangeEnd;
    Integer from;
    Integer size;

}
