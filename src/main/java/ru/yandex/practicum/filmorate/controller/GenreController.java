package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

/**
 * Class controller for genres
 */
@RestController
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    /**
     * GET request handler
     * Get list with all genres
     *
     * @return list with all genres
     */
    @GetMapping(value = "/genres")
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
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
        return genreService.getGenreById(id);
    }
}
