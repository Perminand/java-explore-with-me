package ru.practicum.ewm.main.model.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.main.model.category.dto.CategoryDto;
import ru.practicum.ewm.main.model.users.dto.UserShortDto;

@Getter
@Setter
public class EventShortDto {
    @NotNull
    private String annotation;
    @NotNull
    private CategoryDto category;
    private Long confirmedRequests;
    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;
    private Long id;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private Boolean paid;
    @NotNull
    private String title;
    private Long view;

    public EventShortDto() {
        paid = null;
    }
}
