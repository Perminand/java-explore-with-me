package ru.practicum.ewm.main.service.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.mappers.UserMapper;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.model.users.dto.UserDto;
import ru.practicum.ewm.main.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImp implements UserService {

    private final UserRepository repository;

    @Override
    public UserDto create(UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        repository.save(user);
        return UserMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAll(Set<Long> listIds, Integer from, Integer size) {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        int startPage = from > 0 ? (from / size) : 0;
        Pageable pageable = PageRequest.of(startPage, size, sortById);
        Page<User> users;
        if (listIds != null) {
            if (listIds.isEmpty()) {
                return Collections.emptyList();
            }
            users = repository.findAllByIdsPageable(listIds, pageable);
        } else {
            users = repository.findAll(pageable);
        }

        return users.getContent().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long l) {

        Optional.of(repository.findById(l))
                .orElseThrow(() -> {
                    log.error("Попытка удаления не существующей категории");
                    return new EntityNotFoundException("Пользователь не найден или недоступен");
                });
        repository.deleteById(l);
    }
}
