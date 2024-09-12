package ru.practicum.ewm.main.model.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import ru.practicum.ewm.main.model.Location;
import ru.practicum.ewm.main.model.category.dto.CategoryDto;
import ru.practicum.ewm.main.model.users.dto.UserShortDto;

@AllArgsConstructor
@Builder
public class EventFullDto {
    private Long id;

    @NotBlank
    private String annotation;

    @NotNull
    private CategoryDto category;

    private Long confirmedRequests;

    private String createdOn;

    private String description;

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;

    @NotNull
    private UserShortDto initiator;

    @NotNull
    private Location location;

    @NotNull
    private Boolean paid;

    private Integer participantLimit;

    private String publishedOn;

    private Boolean requestModeration;

    private String state;

    @NotNull
    private String title;

    private Long views;
}
