package ru.practicum.ewm.main.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.StatClient;
import ru.practicum.ewm.main.model.dto.users.UserDto;
import ru.practicum.ewm.main.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final StatClient statClient;
    @Override
    public UserDto create(UserDto userDto) {
        statClient.createStat(new EndpointHitDto());
        userRepository.save(userDto);


        return null;
    }

    @Override
    public List<UserDto> get(List<Long> listIds, Long from, Long size) {
        return null;
    }

    @Override
    public void delete(Long l) {

    }
}
