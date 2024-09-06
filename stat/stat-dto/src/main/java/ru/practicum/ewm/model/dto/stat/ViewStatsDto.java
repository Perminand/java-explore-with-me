package ru.practicum.ewm.model.dto.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ViewStatsDto {
    private String app;
    private String url;
    private Long hits;
}
