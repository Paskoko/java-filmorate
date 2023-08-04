package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.filmorate.util.Util.userValidation;

/**
 * Class controller for users
 */
@RestController
@Slf4j
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private static int id = 0;  // Unique ID for every user

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
        user.setId(++id);
        users.put(id, user);
        return users.get(id);
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
        if (users.containsKey(updatedUser.getId())) {
            users.put(updatedUser.getId(), updatedUser);
            return users.get(updatedUser.getId());
        } else {
            log.warn("User update incorrect.");
            throw new ResourceNotFoundException("No such user!");
        }
    }
}
