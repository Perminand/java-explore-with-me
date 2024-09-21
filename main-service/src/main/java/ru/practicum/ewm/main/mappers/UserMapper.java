package ru.practicum.ewm.main.mappers;

import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.model.users.dto.UserDto;
import ru.practicum.ewm.main.model.users.dto.UserShortDto;

public class UserMapper {
    public static User toEntity(UserDto userDto) {
        return new User(userDto.getId(), userDto.getEmail(), userDto.getName());
    }

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }

    public static UserShortDto toShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }
}

