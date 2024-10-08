package ru.practicum.ewm.main.model.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.model.locations.Location;
import ru.practicum.ewm.main.model.users.User;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "events", schema = "public")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "create_on")
    private LocalDateTime createdOn;

    @NotNull(message = "Пустое описание")
    @Length(min = 20, max = 7000)
    private String description;

    @Column(name = "event_date")
    @NotNull(message = "eventDate не может быть пустым")
    private LocalDateTime eventDate;

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

    @Enumerated(EnumType.STRING)
    private EventStatus state;

    @NotBlank(message = "title не может быть пустым")
    @Length(min = 3, max = 120)
    private String title;
    @Transient
    private Long views;
}
