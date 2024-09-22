package ru.practicum.ewm.exceptions.errors;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String massage) {
        super(massage);
    }
}
