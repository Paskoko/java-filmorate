package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.util.Util.filmValidation;


/**
 * Class controller for films
 */
@RestController
@Slf4j
public class FilmController {

    private static final FilmStorage filmStorage = new InMemoryFilmStorage();

    /**
     * GET request handler
     *
     * @return List with all saved films
     */
    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return filmStorage.getFilms();
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
        return filmStorage.addFilm(film);
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
        return filmStorage.updateFilm(updatedFilm);
    }


}
