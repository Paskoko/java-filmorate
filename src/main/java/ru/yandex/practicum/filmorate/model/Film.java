package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Set;

/**
 * Class with film's components
 */
@Data
@Builder
public class Film {
    private int id;
    @NotBlank
    private String name;
    private String description;
    @Past
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private int rate;
    private MpaRating mpa;
    private Set<Genre> genres;  // Set to check duplicates
}
