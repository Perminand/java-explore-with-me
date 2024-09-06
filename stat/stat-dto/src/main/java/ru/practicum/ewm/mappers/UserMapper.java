package ru.practicum.ewm.mappers;

import ru.practicum.ewm.User;
import ru.practicum.ewm.model.dto.service.UserDto;

public class UserMapper {
    public static User toUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
