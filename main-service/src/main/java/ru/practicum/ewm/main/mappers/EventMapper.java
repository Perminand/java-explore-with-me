package ru.practicum.ewm.main.mappers;

import lombok.RequiredArgsConstructor;
import ru.practicum.GeneralConstants;
import ru.practicum.ewm.main.dto.event.EventDto;
import ru.practicum.ewm.main.dto.event.EventFullDto;
import ru.practicum.ewm.main.dto.event.EventShortDto;
import ru.practicum.ewm.main.model.event.Event;

@RequiredArgsConstructor
public class EventMapper {
    public static Event toEntity(EventDto eventDto) {
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

    public static EventFullDto toFullDto(Event event) {
        return EventFullDto
                .builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .title(event.getTitle())
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .requestModeration(event.getRequestModeration())
                .createdOn(event.getCreatedOn())
                .state(event.getState().toString())
                .build();
    }

    public static EventShortDto toShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toDto(event.getCategory()),
                null,
                event.getEventDate().format(GeneralConstants.DATE_FORMATTER),
                UserMapper.toShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                event.getViews());
    }
}

