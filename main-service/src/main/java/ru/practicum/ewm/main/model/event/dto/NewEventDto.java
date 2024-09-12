package ru.practicum.ewm.main.model.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import ru.practicum.ewm.main.model.Location;

@Getter
public class NewEventDto {
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
    private Boolean paid = false;

    private Integer participantLimit = 0;
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
    private Boolean requestModeration = true;

    public NewEventDto() {
        Boolean paid = false;
        Integer participantLimit = 0;
        Boolean requestModeration = true;
    }
}
