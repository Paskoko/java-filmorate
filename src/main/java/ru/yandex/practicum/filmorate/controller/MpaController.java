package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

/**
 * Class controller for MPA ratings
 */
@RestController
public class MpaController {

    private final MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    /**
     * GET request handler
     * Get list with all MPA ratings
     *
     * @return list of all MPA ratings
     */
    @GetMapping(value = "/mpa")
    public List<MpaRating> getAllMpa() {
        return mpaService.getAllMpa();
    }

    /**
     * GET request handler
     * Get MPA rating by id
     *
     * @param id of MPA rating
     * @return MPA rating
     */
    @GetMapping(value = "/mpa/{id}")
    public MpaRating getMpaRatingById(@PathVariable int id) {
        return mpaService.getMpaRatingById(id);
    }
}
