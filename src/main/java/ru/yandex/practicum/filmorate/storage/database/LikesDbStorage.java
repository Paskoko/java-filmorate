package ru.yandex.practicum.filmorate.storage.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Class storage for film's likes from database
 */
@Component
public class LikesDbStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikesDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * Get likes for the film by id
     *
     * @param filmId of the film
     * @return list of user id who likes the film
     */
    public List<Integer> getLikes(int filmId) {
        String sqlQuery = "select * from likes where film_id = ?";

        return jdbcTemplate.query(sqlQuery,
                (resultSet, rowNumber) -> resultSet.getInt("user_id"),
                filmId);
    }

    /**
     * Set like for film from user
     *
     * @param filmId of film
     * @param userId of user
     * @return true if like was set, false if error
     */
    public boolean setLike(int filmId, int userId) {
        String sqlQuery = "insert into likes (FILM_ID, USER_ID) values (?, ?)";

        return jdbcTemplate.update(sqlQuery, filmId, userId) > 0;
    }


    /**
     * Delete like for film from user
     *
     * @param filmId of film
     * @param userId of user
     */
    public void deleteLike(int filmId, int userId) {
        String sqlQuery = "delete from likes where film_id = ? and user_id = ?";

        jdbcTemplate.update(sqlQuery, filmId, userId);
    }
}
