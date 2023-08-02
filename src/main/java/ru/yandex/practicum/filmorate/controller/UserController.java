package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class controller for users
 */
@RestController
@Slf4j
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private static int ID = 0;  // Unique for every user

    /**
     * GET request handler
     *
     * @return List with all saved users
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * POST request handler with validation
     *
     * @param user to add
     * @return added user
     */
    @PostMapping(value = "/users")
    public User createUser(@Valid @RequestBody User user) {
        userValidation(user);
        user.setID(++ID);
        users.put(ID, user);
        return users.get(ID);
    }

    /**
     * PUT request handler with validation
     *
     * @param updatedUser to update
     * @return updated user
     */
    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User updatedUser) {
        userValidation(updatedUser);
        if (users.containsKey(updatedUser.getID())) {
            users.put(updatedUser.getID(), updatedUser);
            return users.get(updatedUser.getID());
        } else {
            log.warn("User update incorrect.");
            throw new ValidationException("No such user!");
        }
    }

    /**
     * Validation for user's login and empty name
     * Throws custom exception
     *
     * @param user to validate
     */
    private void userValidation(User user) {
        if (user.getLogin().matches(".*\\s+.*")) {
            log.warn("Login validation incorrect.");
            throw new ValidationException("Login must be without spaces!");
        }

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }
}
