package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Model;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;;

@Slf4j
public abstract class Controller<T extends Model> {
    protected final Map<Integer, T> allData = new HashMap<>();
    private int id = 0;

    @GetMapping
    public Collection<T> findAll() {
        return allData.values();
    }

    @PostMapping
    public T create(@Valid @RequestBody T object) throws ValidationException {
        validate(object, "Поля заполнены некорректно!");
        object.setId(setNewId());
        setUserNameAsEmpty(object);
        allData.put(object.getId(), object);
        log.info("Объект создан: " + object);
        return object;
    }

    @PutMapping
    public T update(@Valid @RequestBody T object) throws ValidationException {
        validate(object, "Поля для обновления заполнены некорректно!");
        if (!allData.containsKey(object.getId())) {
            throw new ValidationException("Объекта с данным id нет в списке");
        }
        setUserNameAsEmpty(object);
        allData.put(object.getId(), object);
        log.info("Объект обновлён: " + object);
        return object;
    }

    private int setNewId() {
        return ++id;
    }

    //В случае, когда валидация не совершается, выбрасывается исключение
    private void validate(T object, String message) throws ValidationException {
        if (!doValidate(object)) {
            throw new ValidationException(message);
        }
    }

    //Метод переопределяется для филмов и пользователей
    protected boolean doValidate(T object) {
        return true;
    }

    protected void setUserNameAsEmpty(T object) {}
}
