package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.util.Util.filmValidation;


/**
 * Class controller for films
 */
@RestController
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    /**
     * GET request handler
     *
     * @return List with all saved films
     */
    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return filmService.getFilms();
    }

    /**
     * POST request handler with validation
     *
     * @param film to add
     * @return added film
     */
    @PostMapping(value = "/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        filmValidation(film);   // For FilmControllerTest
        return filmService.addFilm(film);
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
        return filmService.updateFilm(updatedFilm);
    }

    /**
     * GET film by id request handler
     *
     * @param id of film
     * @return film
     */
    @GetMapping(value = "/films/{id}")
    public Film getFilmById(@PathVariable int id) {
        return filmService.getFilmById(id);
    }

    /**
     * PUT request handler
     * Like the film
     *
     * @param id     of the film
     * @param userId of user
     * @return film with like
     */
    @PutMapping(value = "/films/{id}/like/{userId}")
    public Film likeFilm(@PathVariable int id, @PathVariable int userId) {
        return filmService.likeFilm(id, userId);
    }

    /**
     * DELETE request handler
     * Delete like from user
     *
     * @param id     of the film
     * @param userId of user
     * @return film without like
     */
    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public Film deleteLike(@PathVariable int id, @PathVariable int userId) {
        return filmService.deleteLike(id, userId);
    }

    /**
     * GET request handler
     * Get list of popular films
     *
     * @param count of popular films, by default 10
     * @return list of popular films
     */
    @GetMapping(value = "/films/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopular(count);
    }

    /**
     * GET request handler
     * Get list with all MPA ratings
     *
     * @return list of all MPA ratings
     */
    @GetMapping(value = "/mpa")
    public List<MpaRating> getAllMpa() {
        return filmService.getAllMpa();
    }

    /**
     * GET request handler
     * Get MPA rating by id
     *
     * @param id of MPA rating
     * @return MPA rating
     */
    @GetMapping(value = "/mpa/{id}")
    public MpaRating getMpaRatingById(@PathVariable int id) {
        return filmService.getMpaRatingById(id);
    }


    /**
     * GET request handler
     * Get list with all genres
     *
     * @return list with all genres
     */
    @GetMapping(value = "/genres")
    public List<Genre> getAllGenres() {
        return filmService.getAllGenres();
    }

    /**
     * GET request handler
     * Get genre by id
     *
     * @param id of genre
     * @return genre
     */
    @GetMapping(value = "/genres/{id}")
    public Genre getGenreById(@PathVariable int id) {
        return filmService.getGenreById(id);
    }

}
