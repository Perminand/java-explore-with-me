package ru.practicum.ewm.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.model.users.dto.UserDto;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<UserDto> findByIdIn(Set<Long> listIds, Pageable pageable);

    @Query("select u from User u " +
            "where u.id in ?1")
    Page<User> findAllByIdsPageable(Set<Long> listIds, Pageable pageable);
}
