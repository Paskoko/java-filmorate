package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.util.Util.filmValidation;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
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
        filmValidation(film);
        return filmStorage.addFilm(film);
    }

    /**
     * Update film with validation
     *
     * @param film to update
     * @return updated film
     */
    public Film updateFilm(Film film) {
        filmValidation(film);
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


        Set<Integer> likes = film.getLikes();
        if (likes == null) {
            likes = new HashSet<>();
        }
        likes.add(userId);
        film.setLikes(likes);

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

        Set<Integer> likes = film.getLikes();
        if (likes == null) {
            likes = new HashSet<>();
        }
        likes.remove(userId);
        film.setLikes(likes);

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
        if (film0.getLikes() == null) {
            return 1;
        }
        if (film1.getLikes() == null) {
            return -1;
        }
        return film1.getLikes().size() - film0.getLikes().size();
    }

}
