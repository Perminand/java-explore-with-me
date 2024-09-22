package ru.practicum.ewm.main.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import ru.practicum.ewm.main.model.locations.Location;

@Getter
public class NewEventDto {
    private final Boolean paid = false;
    private final Integer participantLimit = 0;
    private final Boolean requestModeration = true;
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Long category;
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;
    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;
    @NotNull
    private Location location;
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
}
