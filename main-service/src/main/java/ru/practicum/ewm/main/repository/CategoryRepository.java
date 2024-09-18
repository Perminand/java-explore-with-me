package ru.practicum.ewm.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.model.category.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    List<Category> findAllByName(String name);

}
