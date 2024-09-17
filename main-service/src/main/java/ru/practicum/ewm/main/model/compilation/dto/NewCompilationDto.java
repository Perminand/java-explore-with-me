package ru.practicum.ewm.main.model.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {

    private List<Long> events;

    private boolean pinned;

    @NotBlank
    @Length(min = 1, max = 50)
    private String title;
}
