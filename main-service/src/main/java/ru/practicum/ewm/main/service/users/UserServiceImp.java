package ru.practicum.ewm.main.service.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.mappers.UserMappers;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.model.users.dto.UserDto;
import ru.practicum.ewm.main.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository repository;

    @Override
    public UserDto create(UserDto userDto) {
        User user = repository.save(UserMappers.toUser(userDto));
        return UserMappers.toUserDto(user);
    }

    @Override
    public List<UserDto> getAll(Set<Long> listIds, Integer from, Integer size) {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        int startPage = from > 0 ? (from / size) : 0;
        Pageable pageable = PageRequest.of(startPage, size, sortById);
        if (listIds == null || listIds.isEmpty()) {
            return repository.findAll().stream().map(UserMappers::toUserDto).toList();
        }
        return repository.findByIdIn(listIds, pageable);
    }

    @Override
    public void delete(Long l) {

        Optional.of(repository.findById(l))
                .orElseThrow(() -> {
                    log.error("Попытка удаления не существующей категории");
                    throw new EntityNotFoundException("Пользователь не найден или недоступен");
                });
        repository.deleteById(l);
    }
}
