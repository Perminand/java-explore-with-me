package ewm.dto.model.dto.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    long id;
    @NotNull
    String name;
    @NotNull
    String email;
}
