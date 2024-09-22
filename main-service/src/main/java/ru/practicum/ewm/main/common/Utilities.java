package ru.practicum.ewm.main.common;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.main.dto.event.EventFullDto;
import ru.practicum.ewm.main.dto.event.EventShortDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@AllArgsConstructor
public class Utilities {
    public static List<EventFullDto> addViewsAndConfirmedRequestsFull(List<EventFullDto> eventFullDtoList,
                                                                      Map<Long, Long> confirmedRequests,
                                                                      List<Long> views) {
        for (int i = 0; i < eventFullDtoList.size(); i++) {
            if ((!views.isEmpty()) && (views.get(i) != 0)) {
                eventFullDtoList.get(i).setViews(views.get(i));
            } else {
                eventFullDtoList.get(i).setViews(0L);
            }
            eventFullDtoList.get(i)
                    .setConfirmedRequests(confirmedRequests
                            .getOrDefault(eventFullDtoList.get(i).getId(), 0L));
        }
        return eventFullDtoList;
    }

    public static <T> List<T> checkTypes(List<?> list, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (Object item : list) {
            try {
                result.add(clazz.cast(item));
            } catch (ClassCastException e) {
                System.out.println(" ");
            }
        }
        return result;
    }

    public static List<EventShortDto> addViewsAndConfirmedRequestsShort(List<EventShortDto> eventShortDtoList, Map<Long, Long> confirmedRequestsByEvents, List<Long> views) {
        for (int i = 0; i < eventShortDtoList.size(); i++) {
            if ((!views.isEmpty()) && (views.get(i) != 0)) {
                eventShortDtoList.get(i).setViews(views.get(i));
            } else {
                eventShortDtoList.get(i).setViews(0L);
            }
            eventShortDtoList.get(i)
                    .setConfirmedRequests(confirmedRequestsByEvents
                            .getOrDefault(eventShortDtoList.get(i).getId(), 0L));
        }
        return eventShortDtoList;
    }

    public static Pageable getPageableSortAscId(Integer from, Integer size) {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        int startPage = from > 0 ? from / size : 0;
        return PageRequest.of(startPage, size, sortById);
    }

    public static Pageable getPageable(Integer from, Integer size) {
        int startPage = from > 0 ? from / size : 0;
        return PageRequest.of(startPage, size);
    }

    public static <T> void setValueIfNull(T params, T value) {
        if (Objects.isNull(params)) {
            params = value;
        }
    }

    public static <T> void setValueIfNotNull(T params, T out, T value) {
        if (Objects.nonNull(params)) {
            out = value;
        }
    }

    public static <T> void setValueIfNull(T params, T value, T value2) {
        if (Objects.isNull(params)) {
            params = value;
        } else {
            params = value2;
        }
    }
}
