package ru.practicum.ewm.main.mappers;

import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.model.users.dto.UserDto;
import ru.practicum.ewm.main.model.users.dto.UserShortDto;

public class UserMappers {
    public static User toUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getEmail(), userDto.getName());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }
}

