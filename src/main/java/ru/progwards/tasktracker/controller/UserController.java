package ru.progwards.tasktracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.repository.dao.impl.UserEntityRepository;
import ru.progwards.tasktracker.repository.dao.impl.UserEntityRepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.UserEntity;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import java.util.Collection;

@RestController
public class UserController {

    private final UserEntityRepository repository;
    private final UserEntityRepositoryUpdateField userEntityRepositoryUpdateField;

    public UserController(UserEntityRepository repository, UserEntityRepositoryUpdateField userEntityRepositoryUpdateField) {
        this.repository = repository;
        this.userEntityRepositoryUpdateField = userEntityRepositoryUpdateField;
    }

    /**
     * по запросу получаем список пользователей
     * @return возвращается список пользователей
     */
    @GetMapping("/rest/user/list")
    public ResponseEntity<Collection<UserEntity>> get() {
        return new ResponseEntity<>(repository.get(), HttpStatus.OK);
    }

    /**
     * по запросу получаем нужного пользователя; если такового нет, то бросаем исключение NotFoundUserException
     * @param id идентификатор пользователя
     * @return User
     */
    @GetMapping("/rest/user/{id}")
    public ResponseEntity<UserEntity> get(@PathVariable("id") Long id) {
        UserEntity entity = repository.get(id);
        if (entity == null)
            throw new NotFoundException("Not found a user with id=" + id);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    /**
     * по запросу создаём пользователя
     * @param entity передаем заполненного пользователя
     * @return возвращаем созданного пользователя
     */
    @PostMapping("/rest/user/create")
    public ResponseEntity<UserEntity> create(@RequestBody UserEntity entity) {
        if (entity == null)
            throw new BadRequestException("User is null");

        repository.create(entity);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    /**
     * по запросу обновляем существующего пользователя
     * @param id идентификатор изменяемого пользователя
     * @param entity измененный пользователь
     */
    @PutMapping("/rest/user/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody UserEntity entity) {
        if (entity == null)
            throw new BadRequestException("User is null");

        entity.setId(id);
        repository.update(entity);
    }

    /**
     * по запросу удаляем нужного пользователя; если такого пользователя не существует,
     * то бросаем исключение NotFoundUserException
     * @param id идентификатор удаляемого пользователя
     */
    @DeleteMapping("/rest/user/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        UserEntity entity = repository.get(id);
        if (entity == null)
            throw new NotFoundException("Not found a user with id=" + id);

        repository.delete(id);
    }

    /**
     * по запросу обновляем значение поля пользователя
     * @param id идентификатор пользователя, в котором нужно обновить поле
     * @param updateOneValue объект, содержащий информацию о поле,
     * которое необходимо изменить и нововое значение данного поля
     */
    @PutMapping("/rest/user/{id}/update1field")
    @ResponseStatus(HttpStatus.OK)
    public void updateOneField(@PathVariable("id") Long id, @RequestBody UpdateOneValue updateOneValue) {
        if (updateOneValue == null)
            throw new BadRequestException("UpdateOneValue is null");

        updateOneValue.setId(id);
        userEntityRepositoryUpdateField.updateField(updateOneValue);
    }
}