package ru.practicum.ewm.main.model.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.users.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests", schema = "public")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "users")
    private User requester;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
