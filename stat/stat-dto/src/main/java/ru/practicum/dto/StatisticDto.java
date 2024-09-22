package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatisticDto {
    @NotBlank(message = "Пустое название приложения")
    private String app;
    @NotBlank(message = "Пустой путь")
    private String uri;
    @NotBlank(message = "Пустой ip")
    private String ip;
    @NotNull(message = "Пустое время")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}