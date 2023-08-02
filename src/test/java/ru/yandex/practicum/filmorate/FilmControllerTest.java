package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FilmController.class)
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FilmController filmController;
    private final Film film = new Film();

    @BeforeEach
    void setFilm() {
        film.setID(1);
        film.setName("Name");
        film.setDescription("Test film.");
        film.setReleaseDate(LocalDate.of(2009, 5, 28));
        film.setDuration(100);
        film.setRate(5);
    }

    @Test
    void checkName() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk());
    }

    @Test
    void checkIncorrectName() throws Exception {
        film.setName(null);

        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkBlankName() throws Exception {
        film.setName("");

        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkFutureReleaseDate() throws Exception {
        film.setReleaseDate(LocalDate.of(2025, 10, 5));

        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkPastReleaseDate() throws Exception {
        film.setReleaseDate(LocalDate.of(1998, 10, 5));

        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk());
    }

    @Test
    void checkDescription() {
        film.setDescription("It's a really long description for that test film. It's not so good, " +
                "but it's long enough to pass that test. I hope so. By the way, the plot of the film is amazing, " +
                "variable twists and perfect actors. You must see it!");

        Assertions.assertThrows(RuntimeException.class, () -> {
            filmController.addFilm(film);
        });
    }

    @Test
    void checkIncorrectReleaseDate() {
        film.setReleaseDate(LocalDate.of(1801, 10, 5));

        Assertions.assertThrows(RuntimeException.class, () -> {
            filmController.addFilm(film);
        });
    }

    @Test
    void checkDuration() throws Exception {
        film.setDuration(-10);

        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }


}