package ru.yandex.practicum.filmorate.exceptions;

/**
 * Custom exception for resources
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
