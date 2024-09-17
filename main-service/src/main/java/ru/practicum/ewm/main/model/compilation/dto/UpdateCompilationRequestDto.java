package ru.practicum.ewm.main.model.compilation.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateCompilationRequestDto {

    private List<Long> events;

    private Boolean pinned;

    @Length(min = 1, max = 50)
    private String title;
}
