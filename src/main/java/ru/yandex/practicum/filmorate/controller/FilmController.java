package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.filmorate.util.Util.filmValidation;


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
            log.warn("Film update incorrect.");
            throw new ResourceNotFoundException("No such film!");
        }
    }


}
