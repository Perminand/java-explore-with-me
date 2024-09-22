package ru.practicum.ewm.main.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.main.model.request.RequestStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    private Long id;
    private String created;
    private Long event;
    private Long requester;
    private RequestStatus status;
}
