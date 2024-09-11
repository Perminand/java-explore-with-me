package ru.practicum.ewm.main.controllers.adminApi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.model.dto.users.UserDto;
import ru.practicum.ewm.main.service.users.UserService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
@Validated
public class UserAdminController {
    private final UserService userService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        log.info("POST запрос на сохранение пользователя");
        return userService.create(userDto);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(
            @RequestParam(name = "ids") List<Long> listIds,
            @RequestParam(name = "from", defaultValue = "0") @Min(0) Long from,
            @RequestParam(name = "size", defaultValue = "10") @Min(0) Long size) {
        log.info("Get Запрос на получение пользователей");
        return userService.get(listIds, from , size);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@Min(0) Long userId) {
        log.info("Delete запрос на удаление категории");
        userService.delete(userId);
    }
}
