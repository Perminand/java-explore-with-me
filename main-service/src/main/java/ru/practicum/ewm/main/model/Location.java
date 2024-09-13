package ru.practicum.ewm.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

@Entity
@Table(name = "locations")
@ToString
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "lat не должен быть null")
    private Float lat;

    @NotNull(message = "lon не должен быть null")
    private Float lon;
}
