package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.database.FriendsDbStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class service for operations with users storage
 */
@Service
public class UserService {

    @Qualifier("userDbStorage")
    private final UserStorage userStorage;

    private final FriendsDbStorage friendsDbStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendsDbStorage friendsDbStorage) {
        this.userStorage = userStorage;
        this.friendsDbStorage = friendsDbStorage;
    }

    /**
     * Get list with all users
     *
     * @return list with all users
     */
    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    /**
     * Add new user with validation
     *
     * @param user to add
     * @return added user
     */
    public User createUser(User user) {
        return userStorage.addUser(user);
    }

    /**
     * Update user with validation
     *
     * @param user to update
     * @return updated user
     */
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    /**
     * Get user by id
     *
     * @param id of user
     * @return user
     */
    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    /**
     * Add new friend to user
     * Add user as a friend to friend
     * Validation of id's
     *
     * @param userId   of user
     * @param friendId of added friend
     */
    public void addNewFriend(int userId, int friendId) {
        getUserById(userId);    // Check user id
        getUserById(friendId);  // Check friend id

        friendsDbStorage.addFriend(userId, friendId, "new");
    }

    /**
     * Delete user's friend
     * Delete friend's user
     * Validation of id's
     *
     * @param userId   of user
     * @param friendId of deleted friend
     */
    public void deleteFriend(int userId, int friendId) {
        getUserById(userId);    // Check user id
        getUserById(friendId);  // Check friend id

        friendsDbStorage.deleteFriend(userId, friendId);
    }

    /**
     * Get friends list
     *
     * @param userId of user
     * @return friends list
     */
    public List<User> getUsersFriends(int userId) {
        List<Integer> friendList = friendsDbStorage.getFriends(userId);

        return getUsers().stream()
                .filter(user ->
                        friendList.contains(user.getId())
                ).collect(Collectors.toList());
    }

    /**
     * Get list of common friends
     *
     * @param userId       of user
     * @param commonUserId of second user
     * @return list of common friends
     */
    public List<User> getCommonFriends(int userId, int commonUserId) {
        List<User> userList = getUsersFriends(userId);
        List<User> commonUserList = getUsersFriends(commonUserId);

        if (userList.isEmpty() || commonUserList.isEmpty()) {
            return new ArrayList<>();
        }

        return userList.stream()
                .filter(commonUserList::contains)
                .collect(Collectors.toList());
    }
}
