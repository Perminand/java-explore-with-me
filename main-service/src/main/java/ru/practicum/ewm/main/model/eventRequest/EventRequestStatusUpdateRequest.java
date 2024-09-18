package ru.practicum.ewm.main.model.eventRequest;

import lombok.Getter;
import ru.practicum.ewm.main.model.status.State;

import java.util.List;

@Getter
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private State status;
}
