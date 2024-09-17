package ru.practicum.ewm.main.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import ru.practicum.ewm.main.exceptions.errors.ConflictException;
import ru.practicum.ewm.main.exceptions.errors.ValidationException;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundError(final EntityNotFoundException e) {
        log.error("404 {}", e.getMessage(), e);
        return setApiError(e, HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictError(final ConflictException e) {
        log.error("409 {}", e.getMessage(), e);
        return setApiError(e, HttpStatus.CONFLICT.getReasonPhrase());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleInternetServerError(final ValidationException e) {
        log.error("500 {}", e.getMessage(), e);
        return setApiError(e, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    private ApiError setApiError(Throwable e, String status) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        return new ApiError(stackTrace, status, e.toString(), e.getMessage());
    }
}
