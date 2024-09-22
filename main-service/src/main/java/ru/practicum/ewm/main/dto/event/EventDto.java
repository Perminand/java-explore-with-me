package ru.practicum.ewm.main.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.main.model.locations.Location;

import java.time.LocalDateTime;

@Getter
@ToString
public class EventDto {

    private Long id;

    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    @NotNull
    private Long category;

    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Location location;

    @Setter
    private Boolean paid;

    @Setter
    @Min(0)
    private Integer participantLimit;

    @Setter
    private Boolean requestModeration;

    @NotBlank
    @Size(min = 3, max = 120)

    private String title;
}
