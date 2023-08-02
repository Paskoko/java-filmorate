package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * Class with film's components
 */
@Data
public class Film {
    private int ID;
    @NotBlank
    private String name;
    private String description;
    @Past
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private int rate;
}
