package ru.practicum.ewm.model.dto.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UserShortDto {
    @NotNull
    @Positive
    long id;
    @NotNull
    String name;
}
