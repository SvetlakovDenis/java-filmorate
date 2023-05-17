package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.xml.bind.ValidationException;
import java.time.Duration;
import java.time.LocalDate;

public class FilmControllerTest {
    static FilmController filmController;

    @BeforeEach
    public void beforeEach() {
        filmController = new FilmController();
    }

    @Test
    void shouldReturnExceptionMessageWhenFieldsOfCreatingFilmAreNotCorrect() {
        Film film = new Film(1, null, "Режисер: Мартин Скорцезе", LocalDate.of(2013, 12, 25), Duration.ofHours(3));
        ValidationException e = Assertions.assertThrows(ValidationException.class, () -> filmController.create(film));
        Assertions.assertEquals("Поля заполнены некорректно!" ,e.getMessage());
    }

    @Test
    void shouldReturnExceptionMessageWhenFieldsOfResettingFilmAreNotCorrect() {
        Film film = new Film(1, "Волк с Уолл-Стрит", "Режисер: Мартин Скорцезе", LocalDate.of(2013, 12, 25),
                Duration.ofHours(3));
        filmController.create(film);
        Film filmToReset = new Film(1, null, "Режисер: Мартин Скорцезе", LocalDate.of(2013, 12, 25),
                Duration.ofHours(3));
        ValidationException e = Assertions.assertThrows(ValidationException.class,
                () -> filmController.update(filmToReset));
        Assertions.assertEquals("Поля для обновления заполнены некорректно!", e.getMessage());
    }

    @Test
    void shouldNotResetNotExistedFilm() {
        Film film = new Film(1, "Волк с Уолл-Стрит", "Режисер: Мартин Скорцезе", LocalDate.of(2013, 12, 25),
                Duration.ofHours(3));
        filmController.create(film);
        Film filmToReset = new Film(2, "Отступники", "Режисер: Мартин Скорцезе", LocalDate.of(2006, 9, 26),
                Duration.ofHours(3));
        ValidationException e = Assertions.assertThrows(ValidationException.class,
                () -> filmController.update(filmToReset));
        Assertions.assertEquals("Объекта с данным id нет в списке", e.getMessage());
    }
}
