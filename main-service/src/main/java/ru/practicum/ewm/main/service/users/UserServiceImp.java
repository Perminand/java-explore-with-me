package ru.practicum.ewm.main.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.mappers.UserMapper;
import ru.practicum.ewm.main.model.User;
import ru.practicum.ewm.main.model.dto.users.UserDto;
import ru.practicum.ewm.main.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDto) {
        User user = userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> get(List<Long> listIds, Long from, Long size) {
        return null;
    }

    @Override
    public void delete(Long l) {

    }
}
