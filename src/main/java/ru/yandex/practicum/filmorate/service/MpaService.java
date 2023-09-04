package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.database.MpaDbStorage;

import java.util.List;

/**
 * Class service for operation with MPA rating storage
 */
@Service
public class MpaService {

    private final MpaDbStorage mpaDbStorage;

    @Autowired
    public MpaService(MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    /**
     * Get list of all MPA ratings
     *
     * @return list of MPA ratings
     */
    public List<MpaRating> getAllMpa() {
        return mpaDbStorage.getAllMpaRatings();
    }

    /**
     * Get MPA rating by id
     *
     * @param id of MPA rating
     * @return MPA rating
     */
    public MpaRating getMpaRatingById(int id) {
        return mpaDbStorage.getMpaRatingById(id);
    }

}
