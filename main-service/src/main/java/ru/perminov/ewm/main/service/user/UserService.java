package ru.perminov.ewm.main.service.user;

import ru.practicum.ewm.model.dto.service.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUser(int[] ids, int from, int size);
}
