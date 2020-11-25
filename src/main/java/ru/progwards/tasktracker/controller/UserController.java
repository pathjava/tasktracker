package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.UserDtoFull;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Обработка запросов API по работе с пользователями
 *
 * @author Aleksandr Sidelnikov
 */
@RestController
@RequestMapping("/rest/user")
public class UserController {

    @Autowired
    GetService<Long, User> getService;
    @Autowired
    CreateService<User> createService;
    @Autowired
    RemoveService<User> removeService;
    @Autowired
    RefreshService<User> refreshService;
    @Autowired
    GetListService<User> getListService;
    @Autowired
    Converter<User, UserDtoFull> dtoConverter;

    /**
     * Получить список всех пользователей
     * GET /rest/user/list
     *
     * @return список пользователей
     */
    @GetMapping("/list")
    public ResponseEntity<Collection<UserDtoFull>> getList() {
        // получили список бизнес-объектов
        Collection<User> list = getListService.getList();
        List<UserDtoFull> resultList = new ArrayList<>(list.size());
        // преобразуем к dto
        for (User entity:list) {
            UserDtoFull dto = dtoConverter.toDto(entity);
            resultList.add(dto);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    /**
     * Получить пользователя по id
     * GET /rest/user/{id}
     *
     * @param id идентификатор объекта
     * @return объект dto
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDtoFull> get(@PathVariable("id") Long id) {
        if (id == null)
            throw new BadRequestException("Id is not set");

        User vo = getService.get(id);
        UserDtoFull entity = dtoConverter.toDto(vo);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    /**
     * Создаём нового пользователя
     * POST /rest/user/create
     *
     * @param entity объект для создания
     * @return объект после бизнес-логики
     */
    @PostMapping("/create")
    public ResponseEntity<UserDtoFull> create(@RequestBody UserDtoFull entity) {
        if (entity == null)
            throw new BadRequestException("User is null");

        User vo = dtoConverter.toModel(entity);
        createService.create(vo);
        UserDtoFull result = dtoConverter.toDto(vo);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Обновляем спользователей
     * PUT /rest/user/{id}/update
     *
     * @param id идентификатор изменяемого объекта
     * @param entity измененный объект
     */
//    @PostMapping("/{id}/update")
    @PutMapping("/{id}/update")
    //  TODO            entity.setId(id); //    id в entity другой
    public ResponseEntity<UserDtoFull> update(@PathVariable("id") Long id,
                                              @RequestBody UserDtoFull entity) {
        if (id == null)
            throw new BadRequestException("Id is not set");
        if (entity == null)
            throw new BadRequestException("User Id is null");

        entity.setId(id);
        User vo = dtoConverter.toModel(entity);
        refreshService.refresh(vo);
        UserDtoFull result = dtoConverter.toDto(vo);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Удалить существующего пользователя
     * DELETE /rest/user/{id}/delete
     *
     * @param id идентификатор удаляемого объекта
     */
//    @PostMapping("/delete")
    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        if (id == null)
            throw new BadRequestException("Workflow Id is not set");

        User vo = getService.get(id);
        removeService.remove(vo);
    }

}
