package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmTest {

    @Autowired
    private TestRestTemplate restTemplate;

    //Тесты на выпадение исключения при различных ошибках валидации
    @Test
    void shouldCreateFilmWithCorrectFields() {
        Film film = new Film(1, "Волк с Уолл-Стрит", "Режисер: Мартин Скорцезе", LocalDate.of(2013, 12, 25),
                Duration.ofHours(3));
        ResponseEntity<Film> response = restTemplate.postForEntity("/film", film, Film.class);

        Assertions.assertEquals("201 CREATED", response.getStatusCode().toString());
    }

    @Test
    void shouldNotCreateFilmWithEmptyName() {
        Film film = new Film(1, null, "Режисер: Мартин Скорцезе", LocalDate.of(2013, 12, 25), Duration.ofHours(3));
        ResponseEntity<Film> response = restTemplate.postForEntity("/film", film, Film.class);

        Assertions.assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void shouldNotCreateFilmWithLongDescription() {
        String description = "1234567890".repeat(21);
        Film film = new Film(1, "Волк с Уолл-Стрит", description, LocalDate.of(2013, 12, 25), Duration.ofHours(3));
        ResponseEntity<Film> response = restTemplate.postForEntity("/film", film, Film.class);

        Assertions.assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void shouldNotCreateFilmWithWrongReleaseDate() {
        Film film = new Film(1, "Волк с Уолл-Стрит", "Режисер: Мартин Скорцезе", LocalDate.of(1013, 12, 25),
                Duration.ofHours(3));
        ResponseEntity<Film> response = restTemplate.postForEntity("/film", film, Film.class);

        Assertions.assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void shouldNotCreateFilmWithNegativeDuration() {
        Film film = new Film(1, "Волк с Уолл-Стрит", "Режисер: Мартин Скорцезе", LocalDate.of(2013, 12, 25),
                Duration.ofHours(3).negated());
        ResponseEntity<Film> response = restTemplate.postForEntity("/film", film, Film.class);

        Assertions.assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }
}
