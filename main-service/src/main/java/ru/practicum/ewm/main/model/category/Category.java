package ru.practicum.ewm.main.model.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "categories", schema = "public")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @NotBlank(message = "Имя категории не может быть пустым")
    @Length(min = 1, max = 50, message = "Длина строки должна быть от 1 до 50 символов")
    @Column(unique = true)
    private String name;

}
