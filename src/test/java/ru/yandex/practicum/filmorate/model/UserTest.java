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
public class UserTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldCreateUserWithCorrectFields() {
        User user = new User(1, "svetlakov@yandex.ru", "Denis", "Denis", LocalDate.of(1997, 9, 5));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        Assertions.assertEquals("201 CREATED", response.getStatusCode().toString());
    }

    @Test
    public void shouldNotCreateUserWithWrongEmail() {
        User user = new User(1, "svetlakov.ru", "Denis", "Denis", LocalDate.of(1997, 9, 5));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        Assertions.assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    public void shouldNotCreateUserWithoutLogin() {
        User user = new User(1, "svetlakov@yandex.ru", null, "Denis", LocalDate.of(1997, 9, 5));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        Assertions.assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    public void shouldNotCreateUserWithWhitespaceInLogin() {
        User user = new User(1, "svetlakov@yandex.ru", "Svetlakov Denis", "Denis", LocalDate.of(1997, 9, 5));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        Assertions.assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    public void shouldNotCreateUserWithFutureDayOfBirthday() {
        User user = new User(1, "svetlakov@yandex.ru", "Denis", "Denis", LocalDate.now().plus(Duration.ofDays(10)));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        Assertions.assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }
}
