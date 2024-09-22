package ru.practicum.ewm.main.service.users;

import ru.practicum.ewm.main.model.users.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserDto create(UserDto categoryDto);

    List<UserDto> getAll(Set<Long> listIds, Integer from, Integer size);

    void delete(Long l);

}
