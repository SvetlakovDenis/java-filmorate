package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserControllerTest {
    UserController userController;

    @BeforeEach
    public void beforeEach() {
        userController = new UserController();
    }

    @Test
    void shouldSetUserNameIfItIsEmpty() {
        User user = new User(1, "denisSvetlakov@yandex.ru", "Denis", null, LocalDate.of(1997, 9, 5));
        userController.create(user);
        Assertions.assertEquals("Denis", user.getLogin());
    }
}
