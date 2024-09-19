package ru.practicum.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatsDto {
    private String app;
    private String uri;
    private Long hits;
}
