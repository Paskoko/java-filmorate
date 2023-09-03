package ru.yandex.practicum.filmorate;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.database.FilmDbStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTests {
    private final FilmDbStorage filmDbStorage;
    private final Film testFilm = Film.builder().build();

    @BeforeEach
    void setTestFilm() {
        testFilm.setId(1);
        testFilm.setName("Name");
        testFilm.setDescription("Test film.");
        testFilm.setReleaseDate(LocalDate.of(2009, 5, 28));
        testFilm.setDuration(100);
        testFilm.setRate(5);
        testFilm.setMpa(MpaRating.builder().id(1).name("G").build());
        testFilm.setGenres(new HashSet<>());
    }

    @Test
    public void testGetAllFilms() {
        int currentSize = filmDbStorage.getFilms().size();

        filmDbStorage.addFilm(testFilm);
        Film newFilm = Film.builder()
                .name("New film")
                .description("New description")
                .releaseDate(LocalDate.of(2000, 12, 31))
                .duration(120)
                .rate(1)
                .mpa(MpaRating.builder().id(1).name("G").build())
                .build();

        filmDbStorage.addFilm(newFilm);

        Optional<List<Film>> filmOptional = Optional.ofNullable(filmDbStorage.getFilms());

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(filmList ->
                        assertThat(filmList.size()).isEqualTo(currentSize + 2)
                );
    }

    @Test
    public void testGetFilmById() {
        filmDbStorage.addFilm(testFilm);
        Optional<Film> filmOptional = Optional.ofNullable(filmDbStorage.getFilmById(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testAddFilm() {
        Optional<Film> filmOptional = Optional.ofNullable(filmDbStorage.addFilm(testFilm));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).isEqualTo(testFilm)
                );
    }

    @Test
    public void testUpdateFilm() {
        testFilm.setName("Updated");
        Optional<Film> filmOptional = Optional.ofNullable(filmDbStorage.updateFilm(testFilm));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "Updated")
                );
    }
}
