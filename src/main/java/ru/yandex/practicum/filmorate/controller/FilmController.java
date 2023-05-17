package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends Controller<Film> {
    private final static LocalDate START_OF_FILMS_HISTORY = LocalDate.of(1895, 12, 28);

    //Проверяется соответствие даты выхода фильма на то, что она не раньше начала истории фильмографии
    @Override
    public boolean doValidate(Film film) {
        return !film.getReleaseDate().isBefore(START_OF_FILMS_HISTORY);
    }
}
