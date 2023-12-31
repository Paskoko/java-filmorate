package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.util.Util.userValidation;


/**
 * Class controller for users
 */
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET request handler
     *
     * @return List with all saved users
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    /**
     * POST request handler with validation
     *
     * @param user to add
     * @return added user
     */
    @PostMapping(value = "/users")
    public User createUser(@Valid @RequestBody User user) {
        userValidation(user);   // For UserControllerTest
        return userService.createUser(user);
    }

    /**
     * GET user by id request handler
     *
     * @param id of user
     * @return user
     */
    @GetMapping(value = "/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    /**
     * PUT request handler with validation
     *
     * @param updatedUser to update
     * @return updated user
     */
    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User updatedUser) {
        userValidation(updatedUser);   // For UserControllerTest
        return userService.updateUser(updatedUser);
    }

    /**
     * PUT request handler
     * Add friend for user
     *
     * @param id       of user
     * @param friendId to add
     */
    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addNewFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addNewFriend(id, friendId);
    }

    /**
     * DELETE request handler
     * Delete user's friend
     *
     * @param id       of user
     * @param friendId to delete
     */
    @DeleteMapping(value = "users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriend(id, friendId);
    }

    /**
     * GET user's friend list request handler
     *
     * @param id of user
     * @return friends list
     */
    @GetMapping(value = "users/{id}/friends")
    public List<User> getUsersFriends(@PathVariable int id) {
        return userService.getUsersFriends(id);
    }

    /**
     * GET request handler
     * List with common friends
     *
     * @param id      of user
     * @param otherId of other user
     * @return list with common friends
     */
    @GetMapping(value = "users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}
