package ru.practicum.ewm.model.dto.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CompilationDTO {
    @NotNull
    @Positive
    long id;
    @NotNull
    boolean pinned;
    @NotNull
    String title;



}
