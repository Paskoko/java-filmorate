package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

/**
 * Class for MPA rating
 */
@Data
@Builder
public class MpaRating {
    private int id;
    private String name;
}
