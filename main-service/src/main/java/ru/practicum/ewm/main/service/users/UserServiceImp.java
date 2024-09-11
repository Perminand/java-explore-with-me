package ru.practicum.ewm.main.service.users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.mappers.UserMapper;
import ru.practicum.ewm.main.model.User;
import ru.practicum.ewm.main.model.dto.users.UserDto;
import ru.practicum.ewm.main.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public List<UserDto> getAll(Set<Long> listIds, Integer from, Integer size) {
        int startPage = from > 0 ? (from / size) : 0;
        Pageable pageable = PageRequest.of(startPage, size);
        if(listIds.isEmpty()) {
            return userRepository.findAll().stream().map(UserMapper::toUserDto).toList();
        }
        return userRepository.findByIdIn(listIds, pageable);
    }

    @Override
    public void delete(Long l) {
        Optional.ofNullable(userRepository.findById(l)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден или недоступен")));
        userRepository.deleteById(l);
    }
}
