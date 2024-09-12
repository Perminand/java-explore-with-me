package ru.practicum.ewm.main.model.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.main.model.Location;
import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.model.category.dto.CategoryDto;
import ru.practicum.ewm.main.model.users.User;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "create_on")
    private String createdOn;

    @NotNull(message = "Пустое описание")
    @Length(min = 20, max = 7000)
    private String description;

    @Column(name = "event_date")
    @NotNull(message = "eventDate не может быть пустым")
    @FutureOrPresent(message = "eventDate должно быть в будущем")
    private String eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "location")
    private Location location;

    @NotNull(message = "paid должен быть true или false")
    private Boolean paid;

    @Column(name = "participants_limit")
    @Min(value = 0, message = "participantLimit должен быть больше 0")
    private Integer participantLimit;

    @Column(name = "published_on")
    private String publishedOn;

    @Column(name = "request_moderation")
    @NotNull(message = "requestModeration должен быть true или false")
    private Boolean requestModeration;

    private String state;

    @NotBlank(message = "title не может быть пустым")
    @Length(min = 3, max = 120)
    private String title;
    private Long views;
}
