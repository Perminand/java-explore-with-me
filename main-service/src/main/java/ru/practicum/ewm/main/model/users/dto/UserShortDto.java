package ru.practicum.ewm.main.model.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserShortDto {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    private String name;
}
