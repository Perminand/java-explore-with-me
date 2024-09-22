package ru.practicum.ewm.main.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.main.dto.category.CategoryDto;
import ru.practicum.ewm.main.model.users.dto.UserShortDto;

@Getter
@Setter
@AllArgsConstructor
public class EventShortDto {
    private Long id;
    @NotNull
    private String annotation;
    @NotNull
    private CategoryDto category;
    private Long confirmedRequests;
    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private Boolean paid;
    @NotNull
    private String title;
    private Long views;

    public EventShortDto() {
        paid = null;
    }
}
