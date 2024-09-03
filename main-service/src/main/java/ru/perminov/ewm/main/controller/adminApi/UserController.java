package ru.perminov.ewm.main.controller.adminApi;

import ewm.dto.model.dto.service.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.perminov.ewm.main.service.user.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUser(
            @RequestParam(name = "ids", required = false) int[] ids,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return userService.getUser(ids, from, size);

    }

}
