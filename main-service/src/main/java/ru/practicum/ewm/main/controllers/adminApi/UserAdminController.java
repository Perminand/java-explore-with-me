package ru.practicum.ewm.main.controllers.adminApi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.model.users.dto.UserDto;
import ru.practicum.ewm.main.service.users.UserService;

import java.util.Collection;
import java.util.Set;

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
    public Collection<UserDto> getUsers(
            @RequestParam(name = "ids", required = false) Set<Long> listIds,
            @RequestParam(name = "from", defaultValue = "0") @Min(0) Integer from,
            @RequestParam(name = "size", defaultValue = "10") @Min(0) Integer size) {
        log.info("Get Запрос на получение пользователей");
        return userService.getAll(listIds, from , size);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @NotNull @Min(0) Long userId) {
        log.info("Delete запрос на удаление пользователя");
        userService.delete(userId);
    }
}
