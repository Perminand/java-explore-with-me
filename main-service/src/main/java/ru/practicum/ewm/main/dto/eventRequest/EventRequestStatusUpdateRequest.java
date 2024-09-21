package ru.practicum.ewm.main.dto.eventRequest;

import lombok.Getter;
import ru.practicum.ewm.main.model.request.RequestStatus;

import java.util.List;

@Getter
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private RequestStatus status;
}
