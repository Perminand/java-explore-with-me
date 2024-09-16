package ru.practicum.ewm.main.mappers;

import lombok.RequiredArgsConstructor;
import ru.practicum.GeneralConstants;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.event.dto.EventDto;
import ru.practicum.ewm.main.model.event.dto.EventFullDto;
import ru.practicum.ewm.main.model.event.dto.EventShortDto;

@RequiredArgsConstructor
public class EventMappers {
    public static Event toEvent(EventDto eventDto) {
        return Event
                .builder()
                .id(eventDto.getId())
                .annotation(eventDto.getAnnotation())
                .description(eventDto.getDescription())
                .eventDate(eventDto.getEventDate())
                .paid(eventDto.getPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .requestModeration(eventDto.getRequestModeration())
                .title(eventDto.getTitle())
                .build();
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto
                .builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMappers.toCategoryDto(event.getCategory()))
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .title(event.getTitle())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .requestModeration(event.getRequestModeration())
                .createdOn(event.getCreatedOn())
                .state(event.getState().toString())
                .build();
    }
    public static EventShortDto toShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMappers.toCategoryDto(event.getCategory()),
                null,
                event.getEventDate().format(GeneralConstants.DATE_FORMATTER),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                event.getViews());
    }
}

