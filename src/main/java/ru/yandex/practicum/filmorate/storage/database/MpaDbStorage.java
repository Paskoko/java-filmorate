package ru.yandex.practicum.filmorate.storage.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class storage for MPA ratings from database
 */
@Component
public class MpaDbStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Get all MPA ratings from database
     *
     * @return list of MPA rating
     */
    public List<MpaRating> getAllMpaRatings() {
        String sqlQuery = "select * from MPA_rating";

        return jdbcTemplate.query(sqlQuery, this::mapRowToMpaRating);
    }

    /**
     * Get MPA rating by id from database
     * With validation
     *
     * @param id of MPA rating
     * @return MPA rating
     */
    public MpaRating getMpaRatingById(int id) {
        String sqlQuery = "select * from MPA_rating where id = ?";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpaRating, id);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("No such MPA rating with that id!");
        }
    }

    /**
     * Implementation of RowMapper for MapRating class
     *
     * @param resultSet row
     * @param rowNumber number of the row
     * @return MPARating object
     * @throws SQLException exception
     */
    private MpaRating mapRowToMpaRating(ResultSet resultSet, int rowNumber) throws SQLException {
        return MpaRating.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
