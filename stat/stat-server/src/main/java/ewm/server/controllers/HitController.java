package ewm.server.controllers;

import ewm.dto.model.dto.stat.EndpointHitDto;
import ewm.server.service.EndpointHitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/hit")
@Validated
@RequiredArgsConstructor
public class HitController {
    private final EndpointHitService endpointHitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto create (@RequestBody EndpointHitDto endpointHitDto) {
        return endpointHitService.create(endpointHitDto);
    }
}
