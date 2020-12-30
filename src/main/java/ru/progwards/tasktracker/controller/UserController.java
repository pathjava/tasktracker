package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.UserDtoFull;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.util.validator.UserEmailValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с пользователями
 *
 * @author Aleksandr Sidelnikov
 */
@RestController
@RequestMapping(value = "/rest/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final @NonNull CreateService<User> createService;
    private final @NonNull GetService<Long, User> getService;
    private final @NonNull GetListService<User> getListService;
    private final @NonNull RemoveService<User> removeService;
    private final @NonNull RefreshService<User> refreshService;
    private final @NonNull Converter<User, UserDtoFull> converter;
    private final @NonNull Converter<User, UserDtoPreview> previewConverter;

    /**
     * Метод создания пользователя
     *
     * @param userDtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданного пользователя
     */
    @PostMapping(value = "/create")
    public ResponseEntity<UserDtoFull> createUser(@Valid @RequestBody UserDtoFull userDtoFull) {
        if (userDtoFull == null)
            throw new BadRequestException("Пустой объект!");

        User user = converter.toModel(userDtoFull);
        createService.create(user);
        UserDtoFull createdUser = converter.toDto(user);

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    /**
     * Метод получения пользователя
     *
     * @param id идентификатор пользователя
     * @return возвращает пользователя
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDtoFull> getUser(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        UserDtoFull user = converter.toDto(getService.get(id));

        if (user == null) //TODO - пустой пользователь или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Пользователь с id: " + id + " не найден!");

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции пользователей
     *
     * @return коллекция Dto пользователей
     */
    @GetMapping(value = "/list")
    public ResponseEntity<List<UserDtoPreview>> getListUser() {
        List<UserDtoPreview> list = getListService.getList().stream()
                .map(previewConverter::toDto)
                .collect(Collectors.toUnmodifiableList());

        if (list.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список пользователей пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод обновления пользователя
     *
     * @param id          идентификатор обновляемого пользователя
     * @param userDtoFull обновляемая сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает обновленного пользователя
     */
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<UserDtoFull> updateUser(@PathVariable Long id,
                                                  @RequestBody UserDtoFull userDtoFull) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        if (!id.equals(userDtoFull.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        User user = converter.toModel(userDtoFull);
        refreshService.refresh(user);
        UserDtoFull updatedUser = converter.toDto(user);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Метод удаления пользователя
     *
     * @param id идентификатор удаляемого пользователя
     * @return возвращает статус ответа
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<UserDtoFull> deleteUser(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        User user = getService.get(id);
        if (user != null)
            removeService.remove(user);
        else
            throw new NotFoundException("Пользователь с id: " + id + " не найден!");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
