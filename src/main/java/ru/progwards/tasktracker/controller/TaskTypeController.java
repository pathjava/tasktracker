package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.TaskType;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с типами задач
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping("/rest/tasktype")
public class TaskTypeController {

    @Autowired
    private CreateService<TaskType> createService;
    @Autowired
    private GetService<Long, TaskType> getService;
    @Autowired
    private GetListService<TaskType> getListService;
    @Autowired
    private RemoveService<TaskType> removeService;
    @Autowired
    private RefreshService<TaskType> refreshService;
    @Autowired
    private GetListByProjectService<Long, TaskType> byProjectService;
    @Autowired
    private Converter<TaskType, TaskTypeDtoFull> converter;

    /**
     * Метод создания типа задачи
     *
     * @param taskTypeDtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданный тип задачи
     */
    @PostMapping("/create")
    public ResponseEntity<TaskTypeDtoFull> createTaskType(@RequestBody TaskTypeDtoFull taskTypeDtoFull) {
        if (taskTypeDtoFull == null)
            throw new BadRequestException("Пустой объект!");

        TaskType taskType = converter.toModel(taskTypeDtoFull);
        createService.create(taskType);
        TaskTypeDtoFull createdTaskType = converter.toDto(taskType);

        return new ResponseEntity<>(createdTaskType, HttpStatus.OK);
    }

    /**
     * Метод получения типа задачи
     *
     * @param id идентификатор типа задачи
     * @return возвращает тип задачи
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskTypeDtoFull> getTaskType(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        TaskTypeDtoFull taskType = converter.toDto(getService.get(id));

        if (taskType == null) //TODO - пустой тип или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Тип задачи с id: " + id + " не найден!");

        return new ResponseEntity<>(taskType, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции типов задач
     *
     * @return коллекция Dto типов задач
     */
    @GetMapping("/list")
    public ResponseEntity<Collection<TaskTypeDtoFull>> getListTaskType() {
        Collection<TaskTypeDtoFull> collection = getListService.getList().stream()
                .map(taskType -> converter.toDto(taskType))
                .collect(Collectors.toUnmodifiableList());

        if (collection.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список типов задач пустой!");

        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    /**
     * Метод обновления типа задачи
     *
     * @param id              идентификатор обновляемого типа задачи
     * @param taskTypeDtoFull обновляемая сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает обновленный тип задачи
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<TaskTypeDtoFull> updateTaskType(@PathVariable Long id,
                                                          @RequestBody TaskTypeDtoFull taskTypeDtoFull) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        if (!id.equals(taskTypeDtoFull.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        TaskType taskType = converter.toModel(taskTypeDtoFull);
        refreshService.refresh(taskType);
        TaskTypeDtoFull updatedTaskType = converter.toDto(taskType);

        return new ResponseEntity<>(updatedTaskType, HttpStatus.OK);
    }

    /**
     * Метод удаления типа задачи
     *
     * @param id идентификатор удаляемого типа задачи
     * @return возвращает статус ответа
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<TaskTypeDtoFull> deleteTaskType(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        TaskType taskType = getService.get(id);
        if (taskType != null)
            removeService.remove(taskType);
        else
            throw new NotFoundException("Тип задачи с id: " + id + " не найден!");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Метод получения коллекции типов задач по идентификатору проекта
     *
     * @param id идентификатор проекта по которому необходимо получить все типы задач данного проекта
     * @return возвращает коллекцию типов задач
     */
    @GetMapping("/{id}/list") //TODO - у данного метода не реализован метод в сервисе
    public ResponseEntity<Collection<TaskTypeDtoFull>> getListTaskType(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Collection<TaskTypeDtoFull> collection = byProjectService.getListByProjectId(id).stream()
                .map(taskType -> converter.toDto(taskType))
                .collect(Collectors.toList());

        if (collection.isEmpty())
            throw new NotFoundException("Список типов задач пустой!");

        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

}
