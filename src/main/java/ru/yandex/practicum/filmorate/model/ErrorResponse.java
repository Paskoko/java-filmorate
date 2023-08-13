package ru.yandex.practicum.filmorate.model;

import lombok.Data;

/**
 * Class for response errors
 */
@Data
public class ErrorResponse {
    private final String error;
}
