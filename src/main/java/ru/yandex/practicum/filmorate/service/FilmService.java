package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.database.LikesDbStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class service for operations with films storage
 */
@Service
public class FilmService {

    @Qualifier("filmDbStorage")
    private final FilmStorage filmStorage;

    @Qualifier("userDbStorage")
    private final UserStorage userStorage;

    private final LikesDbStorage likesDbStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage, LikesDbStorage likesDbStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likesDbStorage = likesDbStorage;
    }


    /**
     * Get list with all films
     *
     * @return list with all films
     */
    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    /**
     * Add new film with validation
     *
     * @param film to add
     * @return added film
     */
    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    /**
     * Update film with validation
     *
     * @param film to update
     * @return updated film
     */
    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    /**
     * Get film by id
     * Validation of id's
     *
     * @param id of film
     * @return film
     */
    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    /**
     * Set like to the film
     *
     * @param filmId of the film
     * @param userId of user
     * @return updated film
     */
    public Film likeFilm(int filmId, int userId) {
        Film film = getFilmById(filmId);
        userStorage.getUserById(userId);    // Check valid user ID

        if (likesDbStorage.setLike(filmId, userId)) {
            film.setRate(film.getRate() + 1);
        }

        return updateFilm(film);
    }

    /**
     * Delete film's like
     * Validation of id's
     *
     * @param filmId of the film
     * @param userId of user
     * @return updated film
     */
    public Film deleteLike(int filmId, int userId) {
        Film film = getFilmById(filmId);
        userStorage.getUserById(userId);    // Check valid user ID

        Set<Integer> likes = new HashSet<>(likesDbStorage.getLikes(filmId));

        if (!likes.isEmpty()) {
            likesDbStorage.deleteLike(filmId, userId);
        }

        film.setRate(film.getRate() - 1);
        return updateFilm(film);
    }

    /**
     * Get list of popular films
     *
     * @param count of popular films
     * @return list of films
     */
    public List<Film> getPopular(int count) {
        return getFilms().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    /**
     * Comparator for sorting popular films
     *
     * @param film0 Film 0
     * @param film1 Film 1
     * @return result of comparing
     */
    private int compare(Film film0, Film film1) {
        if (film0.getRate() == 0) {
            return 1;
        }
        if (film1.getRate() == 0) {
            return -1;
        }
        return film1.getRate() - film0.getRate();
    }

}
