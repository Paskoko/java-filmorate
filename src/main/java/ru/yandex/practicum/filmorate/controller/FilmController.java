package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Class controller for films
 */
@RestController
@Slf4j
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private static int id = 0;  // Unique ID for every film

    /**
     * GET request handler
     *
     * @return List with all saved films
     */
    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    /**
     * POST request handler with validation
     *
     * @param film to add
     * @return added film
     */
    @PostMapping(value = "/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        filmValidation(film);
        film.setId(++id);
        films.put(id, film);
        return films.get(id);
    }

    /**
     * PUT request handler with validation
     *
     * @param updatedFilm to update
     * @return updated film
     */
    @PutMapping(value = "/films")
    public Film updateFilm(@Valid @RequestBody Film updatedFilm) {
        filmValidation(updatedFilm);
        if (films.containsKey(updatedFilm.getId())) {
            films.put(updatedFilm.getId(), updatedFilm);
            return films.get(updatedFilm.getId());
        } else {
            throw new ValidationException("No such film!");
        }
    }

    /**
     * Validation for film's description and release date
     * Throws custom exceptions
     *
     * @param film to validate
     */
    private void filmValidation(Film film) {
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
