package ru.yandex.practicum.filmorate.controller;

/**
 * Custom exception for validation
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}