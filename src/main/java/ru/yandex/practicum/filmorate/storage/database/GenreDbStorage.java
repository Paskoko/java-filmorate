package ru.yandex.practicum.filmorate.storage.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class storage for genres from database
 */
@Component
public class GenreDbStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Get all genres from database
     *
     * @return list of genres
     */
    public List<Genre> getAllGenres() {
        String sqlQuery = "select * from genres";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    /**
     * Get genre by id from database
     * With validation
     *
     * @param id of genre
     * @return genre
     */
    public Genre getGenreById(int id) {
        String sqlQuery = "select * from genres where id = ?";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("No such genre with that id!");
        }
    }

    /**
     * Get all film's genres by film's id
     *
     * @param filmId of the film
     * @return list wit genres
     */
    public List<Genre> getFilmGenres(int filmId) {
        String sqlQuery = "select g.id, g.name  from genres g  \n" +
                "left outer join film_genre fg on g.id = fg.genre_id where fg.film_id = ? order by g.id";


        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre, filmId);
    }

    /**
     * Implementation of RowMapper for Genre class
     *
     * @param resultSet row
     * @param rowNumber number of the row
     * @return Genre object
     * @throws SQLException exception
     */
    private Genre mapRowToGenre(ResultSet resultSet, int rowNumber) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
