package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.UserDtoFull;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.util.validator.verificationstage.Create;
import ru.progwards.tasktracker.util.validator.verificationstage.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с пользователями
 *
 * @author Aleksandr Sidelnikov
 */
@RestController
@RequestMapping(value = "/rest/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
@Validated
public class UserController {

    private final CreateService<User> createService;
    private final GetService<Long, User> getService;
    private final GetListService<User> getListService;
    private final RemoveService<User> removeService;
    private final RefreshService<User> refreshService;
    private final Converter<User, UserDtoFull> converter;
    private final Converter<User, UserDtoPreview> previewConverter;

    /**
     * Метод создания пользователя
     *
     * @param userDtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданного пользователя
     */
    @PostMapping(value = "/create")
    public ResponseEntity<UserDtoFull> createUser(@Validated(Create.class) @RequestBody UserDtoFull userDtoFull) {

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
    public ResponseEntity<UserDtoFull> getUser(@PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id) {

        UserDtoFull user = converter.toDto(getService.get(id));

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
    public ResponseEntity<UserDtoFull> updateUser(@PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id,
                                                  @Validated(Update.class) @RequestBody UserDtoFull userDtoFull) {

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
    public ResponseEntity<UserDtoFull> deleteUser(@PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id) {

        User user = getService.get(id);
        removeService.remove(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
