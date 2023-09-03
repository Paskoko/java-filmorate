package ru.yandex.practicum.filmorate.storage.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


/**
 * Class storage for users from database
 */
@Component("userDbStorage")
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Add user to the database
     *
     * @param user to add
     * @return added user
     */
    @Override
    public User addUser(User user) {
        String sqlQuery = "insert into users (EMAIL, LOGIN, NAME, BIRTHDAY)" +
                "values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sqlQuery, new String[]{"id"});
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getName());
            statement.setString(4, String.valueOf(user.getBirthday()));
            return statement;
        }, keyHolder);

        int userId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return getUserById(userId);
    }

    /**
     * Update user in the database
     *
     * @param user to update
     * @return updated user
     */
    @Override
    public User updateUser(User user) {
        String sqlQuery = "update users set " +
                "EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? " +
                "where id = ?";

        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());

        return getUserById(user.getId());
    }

    /**
     * Get list of all users from database
     *
     * @return list of users
     */
    @Override
    public List<User> getUsers() {
        String sqlQuery = "select * from users";

        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    /**
     * Get user by id from database
     * Validation of user id
     *
     * @param id of user
     * @return user
     */
    @Override
    public User getUserById(int id) {
        String sqlQuery = "select * from users where id = ?";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("No such user with that id!");
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
    private User mapRowToUser(ResultSet resultSet, int rowNumber) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .email(resultSet.getString("email"))
                .name(resultSet.getString("name"))
                .login(resultSet.getString("login"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }
}
