package ru.practicum.ewm.main.exceptions.errors;

public class ClientException extends RuntimeException {
    public ClientException(final String message) {
        super(message);
    }
}
