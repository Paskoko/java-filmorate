package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

/**
 * Class for film's genres
 */
@Data
@Builder
public class Genre {
    private int id;
    private String name;
}
