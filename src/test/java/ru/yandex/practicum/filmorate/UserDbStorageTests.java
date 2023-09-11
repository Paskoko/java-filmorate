package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.database.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTests {
    private final UserDbStorage userStorage;

    private final User testUser = User.builder().build();

    @BeforeEach
    void setTestUser() {
        testUser.setId(1);
        testUser.setEmail("test@mail.com");
        testUser.setLogin("test");
        testUser.setName("test name");
        testUser.setBirthday(LocalDate.of(1999, 12, 31));
    }

    @Test
    public void testGetAllUsers() {
        int currentSize = userStorage.getUsers().size();

        userStorage.addUser(testUser);
        User newUser = User.builder()
                .name("New user")
                .login("New login")
                .email("new@email.com")
                .birthday(LocalDate.of(2000, 12, 31))
                .build();

        userStorage.addUser(newUser);

        Optional<List<User>> userOptional = Optional.ofNullable(userStorage.getUsers());

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(userList ->
                        assertThat(userList.size()).isEqualTo(currentSize + 2)
                );


    }

    @Test
    public void testFindUserById() {
        userStorage.addUser(testUser);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testAddUser() {
        Optional<User> userOptional = Optional.ofNullable(userStorage.addUser(testUser));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).isEqualTo(testUser)
                );

    }

    @Test
    public void testUpdateUser() {
        testUser.setName("Updated");
        Optional<User> userOptional = Optional.ofNullable(userStorage.addUser(testUser));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "Updated")
                );
    }
}