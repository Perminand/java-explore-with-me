package ru.practicum.ewm.main.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.main.model.event.dto.EventFullDto;
import ru.practicum.ewm.main.model.event.dto.EventShortDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utilities {


    //Method allows to handle EventRespShort or EventRespFull
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

    //Method checks type after generics to return particular type to avoid unchecked cast
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
                eventShortDtoList.get(i).setView(views.get(i));
            } else {
                eventShortDtoList.get(i).setView(0L);
            }
            eventShortDtoList.get(i)
                    .setConfirmedRequests(confirmedRequestsByEvents
                            .getOrDefault(eventShortDtoList.get(i).getId(), 0L));
        }
        return eventShortDtoList;
    }
}
