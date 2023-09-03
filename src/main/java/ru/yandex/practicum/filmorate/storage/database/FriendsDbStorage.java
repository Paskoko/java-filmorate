package ru.yandex.practicum.filmorate.storage.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class storage for user's friends from database
 */
@Component
public class FriendsDbStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendsDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Add new friend with status
     *
     * @param userId   of user
     * @param friendId of friend
     * @param status   of friendship
     * @return true if like was set, false if error
     */
    public boolean addFriend(int userId, int friendId, String status) {
        String sqlQuery = "insert into friends (USER_ID, FRIEND_ID, STATUS) values (?, ?, ?)";

        return jdbcTemplate.update(sqlQuery, userId, friendId, status) > 0;
    }

    /**
     * Delete friendship with another user
     *
     * @param userId   of user
     * @param friendId of friend
     */
    public void deleteFriend(int userId, int friendId) {
        String sqlQuery = "delete from friends where user_id = ? and friend_id = ?";

        jdbcTemplate.update(sqlQuery, userId, friendId);
    }


    /**
     * Get list with user's friend id
     *
     * @param userId of user
     * @return list with friend id
     */
    public List<Integer> getFriends(int userId) {
        String sqlQuery = "select friend_id from friends where user_id = ?";

        return jdbcTemplate.query(sqlQuery,
                (resultSet, rowNumber) -> resultSet.getInt("friend_id"),
                userId);
    }
}
