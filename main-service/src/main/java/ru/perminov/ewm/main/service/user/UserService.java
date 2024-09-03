package ru.perminov.ewm.main.service.user;

import ewm.dto.model.dto.service.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUser(int[] ids, int from, int size);
}
