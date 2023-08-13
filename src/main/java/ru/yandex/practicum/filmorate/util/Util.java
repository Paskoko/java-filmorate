package ru.yandex.practicum.filmorate.util;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public final class Util {
    /**
     * Validation for user's login and empty name
     * Throws custom exception
     *
     * @param user to validate
     */
    public static void userValidation(User user) {
        if (user.getLogin().matches(".*\\s+.*")) {
            log.warn("Login validation incorrect.");
            throw new ValidationException("Login must be without spaces!");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    /**
     * Validation for film's description and release date
     * Throws custom exceptions
     *
     * @param film to validate
     */
    public static void filmValidation(Film film) {
        if (film.getDescription().length() > 200) {
            log.warn("Film's description too long.");
            throw new ValidationException("Description must be less than 200 symbols!");
        }

        LocalDate cinemaBDay = LocalDate.of(1895, 12, 28);
        if (!film.getReleaseDate().isAfter(cinemaBDay)) {
            log.warn("Film's release date too old.");
            throw new ValidationException("Release date must be after 1895-12-28");
        }
    }
}
