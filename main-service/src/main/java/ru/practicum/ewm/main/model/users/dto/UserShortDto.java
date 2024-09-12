package ru.practicum.ewm.main.model.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserShortDto {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    private String name;
}
