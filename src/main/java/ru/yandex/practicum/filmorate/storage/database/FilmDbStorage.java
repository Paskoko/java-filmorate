package ru.yandex.practicum.filmorate.storage.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * Class storage for films from database
 */
@Component("filmDbStorage")
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaDbStorage mpaDbStorage;
    private final GenreDbStorage genreDbStorage;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, MpaDbStorage mpaDbStorage, GenreDbStorage genreDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaDbStorage = mpaDbStorage;
        this.genreDbStorage = genreDbStorage;
    }

    /**
     * Add new film to the database
     * Add film's genres
     *
     * @param film to add
     * @return added film
     */
    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "insert into films (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, RATING_ID) \n" +
                " \t values (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sqlQuery, new String[]{"id"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setString(3, String.valueOf((film.getReleaseDate())));
            statement.setString(4, String.valueOf(film.getDuration()));
            statement.setString(5, String.valueOf(film.getRate()));
            statement.setString(6, String.valueOf(film.getMpa().getId()));
            return statement;
        }, keyHolder);

        int filmId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        Set<Genre> genreList = film.getGenres();
        if (genreList != null) {
            for (Genre genre : genreList) {
                String sqlGenresQuery = "insert into film_genre (FILM_ID, GENRE_ID) values (?, ?)";
                jdbcTemplate.update(sqlGenresQuery,
                        filmId,
                        genre.getId());
            }
        }

        return getFilmById(filmId);
    }

    /**
     * Update film in the database
     * Update film's genres
     *
     * @param film to update
     * @return updated film
     */
    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "update films set " +
                "NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, RATE = ?, RATING_ID = ? " +
                "where id = ?";

        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getId(),
                film.getId());

        Set<Genre> genreList = film.getGenres();

        String sqlGenresQuery = "delete from film_genre where film_id = ?";
        jdbcTemplate.update(sqlGenresQuery, film.getId());

        if (genreList != null) {
            for (Genre genre : genreList) {
                sqlGenresQuery = "insert into film_genre (FILM_ID, GENRE_ID) values (?, ?)";
                jdbcTemplate.update(sqlGenresQuery,
                        film.getId(),
                        genre.getId());
            }
        }

        return getFilmById(film.getId());
    }

    /**
     * Get list of all films from database
     *
     * @return list of films
     */
    @Override
    public List<Film> getFilms() {
        String sqlQuery = "select * from films";

        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    /**
     * Get film by id database
     * Validation of film id
     *
     * @param id of film
     * @return film
     */
    @Override
    public Film getFilmById(int id) {
        String sqlQuery = "select * from films where id = ?";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("No such film with that id!");
        }
    }

    /**
     * Implementation of RowMapper for Film class
     *
     * @param resultSet row
     * @param rowNumber number of the row
     * @return MPARating object
     * @throws SQLException exception
     */
    private Film mapRowToFilm(ResultSet resultSet, int rowNumber) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .rate(resultSet.getInt("rate"))
                .mpa(mpaDbStorage.getMpaRatingById(resultSet.getInt("rating_id")))
                .genres(new HashSet<>(genreDbStorage.getFilmGenres(resultSet.getInt("id"))))
                .build();
    }
}
