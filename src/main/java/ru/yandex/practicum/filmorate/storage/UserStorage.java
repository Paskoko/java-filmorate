package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

/**
 * Interface for users storage
 */
public interface UserStorage {


    /**
     * Add user to the storage
     *
     * @param user to add
     * @return added user
     */
    User addUser(User user);

    /**
     * Update user in the storage
     *
     * @param user to update
     * @return updated user
     */
    User updateUser(User user);

    /**
     * Return list with all users
     *
     * @return list with all users
     */
    List<User> getUsers();

    /**
     * Return user by id
     *
     * @param id of user
     * @return user
     */
    User getUserById(int id);
}
