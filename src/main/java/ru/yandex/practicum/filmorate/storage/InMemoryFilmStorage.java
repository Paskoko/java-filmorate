package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class storage for films with basic functions
 */
@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private static int id = 0;  // Unique ID for every film

    /**
     * Add film to the storage
     *
     * @param film to add
     * @return added film
     */
    @Override
    public Film addFilm(Film film) {
        film.setId(++id);
        films.put(id, film);
        return films.get(id);
    }

    /**
     * Update film in the storage
     *
     * @param film to update
     * @return updated film
     */
    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return films.get(film.getId());
        } else {
            log.warn("Film update incorrect.");
            throw new ResourceNotFoundException("No such film!");
        }
    }


    /**
     * Return list with all films
     *
     * @return list with all films
     */
    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    /**
     * Return film by id
     *
     * @param id of film
     * @return film
     */
    @Override
    public Film getFilmById(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            log.warn("Incorrect id.");
            throw new ResourceNotFoundException("No such film with that id!");
        }
    }
}
