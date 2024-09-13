package ru.practicum.ewm.main.model.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.practicum.ewm.main.model.markers.Update;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class CategoryDto {
    @NotNull(groups = Update.class)
    @NotBlank(groups = Update.class)
    private Long id;
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
