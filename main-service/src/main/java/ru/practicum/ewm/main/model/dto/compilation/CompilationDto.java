package ru.practicum.ewm.main.model.dto.compilation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class CompilationDto {
    private List<Long> events;
    @NotNull
    private boolean pinned;
    @NotBlank
    @Length(max = 50, min = 1)
    private String title;

}
