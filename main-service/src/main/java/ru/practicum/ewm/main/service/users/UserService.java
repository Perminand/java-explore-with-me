package ru.practicum.ewm.main.service.users;

import ru.practicum.ewm.main.model.dto.users.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto categoryDto);

    List<UserDto> get(List<Long> listIds, Long from, Long size);

    void delete(Long l);

}
