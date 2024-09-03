package ru.perminov.ewm.main.service.user;

import ewm.dto.mappers.UserMapper;
import ewm.dto.model.dto.service.UserDto;
import ru.perminov.ewm.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    @Override
    public List<UserDto> getUser(int[] ids, int from, int size) {
        if (ids==null) {
            return userRepository.findAll().stream().map(UserMapper::toUserDto).toList();
        }
        return null;
    }
}
