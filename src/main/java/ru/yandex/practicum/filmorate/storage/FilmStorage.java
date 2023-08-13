package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

/**
 * Interface for films storage
 */
public interface FilmStorage {

    /**
     * Add film to the storage
     *
     * @param film to add
     * @return added film
     */
    Film addFilm(Film film);

    /**
     * Update film in the storage
     *
     * @param film to update
     * @return updated film
     */
    Film updateFilm(Film film);

    /**
     * Return list with all films
     *
     * @return list with all films
     */
    List<Film> getFilms();

}
