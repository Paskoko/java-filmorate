package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.database.GenreDbStorage;

import java.util.List;

/**
 * Class service for operation with genres storage
 */
@Service
public class GenreService {

    private final GenreDbStorage genreDbStorage;

    @Autowired
    public GenreService(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    /**
     * Get list of all genres
     *
     * @return list of all genres
     */
    public List<Genre> getAllGenres() {
        return genreDbStorage.getAllGenres();
    }

    /**
     * Get genre by id
     *
     * @param id of genre
     * @return genre
     */
    public Genre getGenreById(int id) {
        return genreDbStorage.getGenreById(id);
    }
}
