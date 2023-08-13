package ru.yandex.practicum.filmorate.exceptions;

/**
 * Custom exception for validation
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}