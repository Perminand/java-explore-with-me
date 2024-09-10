package ru.practicum.ewm.main.model.dto.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import ru.practicum.ewm.main.model.markers.Update;

import javax.swing.*;

public class UserDto {

    @NotNull(groups = Update.class)
    @Positive(groups = Update.class)
    private Long id;
    @NotBlank
    @Size(min = 6, max = 254)
    private String email;
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
}
