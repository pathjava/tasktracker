package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.service.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с типами задач (TaskType)
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping(value = "/rest/tasktype",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskTypeController {

    private final @NonNull CreateService<TaskType> taskTypeCreateService;
    private final @NonNull GetService<Long, TaskType> taskTypeGetService;
    private final @NonNull GetListService<TaskType> taskTypeGetListService;
    private final @NonNull RemoveService<TaskType> taskTypeRemoveService;
    private final @NonNull RefreshService<TaskType> taskTypeRefreshService;
    private final @NonNull Converter<TaskType, TaskTypeDtoFull> converter;
    private final @NonNull GetService<Long, Project> projectGetService;

//    @Autowired
//    private GetListByProjectService<Long, TaskType> byProjectService;


    /**
     * Метод создания типа задачи (TaskType)
     *
     * @param taskTypeDto сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданный TaskTypeDtoFull
     */
    @PostMapping(value = "/create")
    public ResponseEntity<TaskTypeDtoFull> create(@RequestBody TaskTypeDtoFull taskTypeDto) {
        if (taskTypeDto == null)
            throw new BadRequestException("TaskTypeDtoFull == null");

        TaskType taskType = converter.toModel(taskTypeDto);
        taskTypeCreateService.create(taskType);
        TaskTypeDtoFull createdTaskType = converter.toDto(taskType);

        return new ResponseEntity<>(createdTaskType, HttpStatus.OK);
    }

    /**
     * Метод получения типа задачи (TaskType)
     *
     * @param id идентификатор типа задачи
     * @return возвращает TaskTypeDtoFull
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskTypeDtoFull> get(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        TaskTypeDtoFull taskType = converter.toDto(taskTypeGetService.get(id));

        return new ResponseEntity<>(taskType, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции всех типов задач (TaskType)
     *
     * @return лист TaskTypeDtoFull
     */
    @GetMapping(value = "/list")
    public ResponseEntity<List<TaskTypeDtoFull>> getList() {
        List<TaskTypeDtoFull> list = taskTypeGetListService.getList().stream()
                .map(converter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список TaskTypeDtoFull пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции типов задач по идентификатору проекта (TaskType)
     *
     * @param id идентификатор проекта по которому необходимо получить все типы задач данного проекта
     * @return возвращает лист TaskTypeDtoFull
     */
    @GetMapping(value = "/{id}/list") //TODO - у данного метода не реализован метод в сервисе
    public ResponseEntity<List<TaskTypeDtoFull>> getListByProject(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Project project = projectGetService.get(id);
        List<TaskTypeDtoFull> list = project.getTaskTypes().stream()
                .map(converter::toDto)
                .collect(Collectors.toList());

        /* old version */
//        Collection<TaskTypeDtoFull> collection = byProjectService.getListByProjectId(id).stream()
//                .map(taskType -> converter.toDto(taskType))
//                .collect(Collectors.toList());

        if (list.isEmpty())
            throw new NotFoundException("Список TaskTypeDtoFull пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод обновления типа задачи (TaskType)
     *
     * @param id          идентификатор обновляемого типа задачи
     * @param taskTypeDto обновляемая сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает обновленный TaskTypeDtoFull
     */
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<TaskTypeDtoFull> update(@PathVariable Long id, @RequestBody TaskTypeDtoFull taskTypeDto) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        if (!id.equals(taskTypeDto.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        TaskType taskType = converter.toModel(taskTypeDto);
        taskTypeRefreshService.refresh(taskType);
        TaskTypeDtoFull updatedTaskType = converter.toDto(taskType);

        return new ResponseEntity<>(updatedTaskType, HttpStatus.OK);
    }

    /**
     * Метод удаления типа задачи (TaskType)
     *
     * @param id идентификатор удаляемого типа задачи
     * @return возвращает статус ответа
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<TaskTypeDtoFull> delete(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        TaskType taskType = taskTypeGetService.get(id);
        taskTypeRemoveService.remove(taskType);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
