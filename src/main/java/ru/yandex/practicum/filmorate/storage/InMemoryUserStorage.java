package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class storage for users with basic functions
 */
@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> users = new HashMap<>();
    private static int id = 0;  // Unique ID for every user

    /**
     * Add user to the storage
     *
     * @param user to add
     * @return added user
     */
    @Override
    public User addUser(User user) {
        user.setId(++id);
        users.put(id, user);
        return users.get(id);
    }


    /**
     * Update user in the storage
     *
     * @param user to update
     * @return updated user
     */
    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return users.get(user.getId());
        } else {
            log.warn("User update incorrect.");
            throw new ResourceNotFoundException("No such user!");
        }
    }


    /**
     * Return list with all users
     *
     * @return list with all users
     */
    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * Return user by id
     *
     * @param id of user
     * @return user
     */
    @Override
    public User getUserById(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            log.warn("Incorrect id of user: " + id);
            throw new ResourceNotFoundException("No such user with that id!");
        }
    }
}
