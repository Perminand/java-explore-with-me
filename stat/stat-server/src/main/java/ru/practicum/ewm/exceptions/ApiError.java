package ru.practicum.ewm.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiError {
    private final String stack;
    private final HttpStatus httpStatus;
    private final String text;
    private final String error;
}

