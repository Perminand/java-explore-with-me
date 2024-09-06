package ru.practicum.ewm.model.dto.stat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ViewStatsDto {
    private String app;
    private String uri;
    private Long hits;
}
