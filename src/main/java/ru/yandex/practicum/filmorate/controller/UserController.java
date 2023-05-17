package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends Controller<User> {

    @Override
    public void setUserNameAsEmpty(User user) {
        if (user.getUserName() == null) {
            user.setUserName(user.getLogin());
            allData.put(user.getId(), user);
        }
    }

    //Проверяет наличие пробелов в логине пользователя
    @Override
    public boolean doValidate(User user) {
        String login = user.getLogin();
        if (login != null) {
            return !login.contains(" ");
        }
        return true;
    }
}
