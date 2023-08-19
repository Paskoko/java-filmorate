package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class service for operations with users storage
 */
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
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
     * @return updated user
     */
    public User addNewFriend(int userId, int friendId) {
        User user = getUserById(userId);
        User secondUser = getUserById(friendId);    // Second user to update friends list

        Set<Integer> friends = user.getFriends();
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(friendId);
        user.setFriends(friends);

        Set<Integer> secondUserFriends = secondUser.getFriends();
        if (secondUserFriends == null) {
            secondUserFriends = new HashSet<>();
        }
        secondUserFriends.add(userId);
        secondUser.setFriends(secondUserFriends);
        updateUser(secondUser);


        return updateUser(user);
    }

    /**
     * Delete user's friend
     * Delete friend's user
     * Validation of id's
     *
     * @param userId   of user
     * @param friendId of deleted friend
     * @return updated user
     */
    public User deleteFriend(int userId, int friendId) {
        User user = getUserById(userId);
        User secondUser = getUserById(friendId);    // Second user to update friends list

        Set<Integer> friends = user.getFriends();
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.remove(friendId);
        user.setFriends(friends);


        Set<Integer> secondUserFriends = secondUser.getFriends();
        if (secondUserFriends == null) {
            secondUserFriends = new HashSet<>();
        }
        secondUserFriends.remove(userId);
        secondUser.setFriends(secondUserFriends);
        updateUser(secondUser);


        return updateUser(user);
    }

    /**
     * Get friends list
     *
     * @param userId of user
     * @return friends list
     */
    public List<User> getUsersFriends(int userId) {

        Set<Integer> friends = getUserById(userId).getFriends();
        if (friends == null) {
            return new ArrayList<>();
        }
        return getUsers().stream()
                .filter(user ->
                        friends.contains(user.getId())
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
