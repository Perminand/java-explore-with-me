package ru.practicum.ewm.main.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.main.markers.Update;

@Getter
@AllArgsConstructor
public class UserDto {

    @NotNull(groups = Update.class, message = "Ид пользователя не может быть пустым")
    @Positive(groups = Update.class, message = "Ид пользователя должно быть положительным")
    private Long id;

    @Email(message = "Емайл не корректен")
    @NotBlank(message = "Емайл не может быть пустым")
    @Size(min = 6, max = 254, message = "Длина строки от 6 до 254 символов")
    private String email;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 2, max = 250, message = "Длина строки от 2 до 250 символов")
    private String name;
}
