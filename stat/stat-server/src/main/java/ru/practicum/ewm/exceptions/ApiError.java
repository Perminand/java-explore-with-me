package ru.practicum.ewm.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {
    private final String stack;
    private final HttpStatus httpStatus;
    private final String text;
    private final String error;

    public ApiError(HttpStatus status, String error, String message, String stack) {
        this.text = message;
        this.error = error;
        this.httpStatus = status;
        this.stack = stack;
    }
}

