package ru.practicum.ewm.main.model.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class CompilationDto {
    private List<Long> events;
    private boolean pinned;
    @NotBlank
    @Length( min = 1, max = 50)
    private String title;

}
