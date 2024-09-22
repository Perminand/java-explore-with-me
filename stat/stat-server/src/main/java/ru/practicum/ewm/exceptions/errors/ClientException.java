package ru.practicum.ewm.exceptions.errors;

public class ClientException extends RuntimeException {
    public ClientException(final String message) {
        super(message);
    }
}
