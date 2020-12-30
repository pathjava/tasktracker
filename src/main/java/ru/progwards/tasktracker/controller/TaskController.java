package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.TaskDtoFull;
import ru.progwards.tasktracker.dto.TaskDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.UpdateOneValue;
import ru.progwards.tasktracker.service.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с задачами (Task)
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping(value = "/rest")
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class TaskController {

    private final GetService<Long, Task> taskGetService;
    private final GetListService<Task> taskGetListService;
    private final RemoveService<Task> taskRemoveService;
    private final CreateService<Task> taskCreateService;
    private final RefreshService<Task> taskRefreshService;
    private final Converter<Task, TaskDtoPreview> dtoPreviewConverter;
    private final Converter<Task, TaskDtoFull> dtoFullConverter;
    private final GetService<String, Task> byCodeGetService;
    private final UpdateOneFieldService<Task> updateOneFieldService;
    private final GetService<Long, Project> projectGetService;

    /**
     * Метод поиска задачи (Task) по идентификатору
     *
     * @param id идентификатор
     * @return возвращает найденную TaskDtoFull
     */
    @GetMapping(value = "/task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDtoFull> get(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        TaskDtoFull task = dtoFullConverter.toDto(taskGetService.get(id));

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Метод поиска задачи (Task) по текстовому коду
     *
     * @param id текстовый идентификатор (код) задачи, создаваемый на основе префикса проекта
     * @return возвращает найденную TaskDtoFull
     */
    @GetMapping(value = "/task/{id}/getbycode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDtoFull> getByCode(@PathVariable String id) {
        if (id == null)
            throw new BadRequestException("Code не задан или задан неверно!");

        TaskDtoFull task = dtoFullConverter.toDto(byCodeGetService.get(id));

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции задач (Task) по идентификатору проекта
     *
     * @param id идентификатор проекта (Project)
     * @return лист TaskDtoPreview
     */
    @GetMapping(value = "/project/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDtoPreview>> getListByProject(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Project project = projectGetService.get(id);
        List<TaskDtoPreview> list = project.getTasks().stream()
                .map(dtoPreviewConverter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список TaskDtoPreview пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции всех задач (Task)
     *
     * @return лист TaskDtoPreview
     */
    @GetMapping(value = "/task/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDtoPreview>> getList() {

        List<TaskDtoPreview> list = taskGetListService.getList().stream()
                .map(dtoPreviewConverter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список TaskDtoPreview пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод создания задачи (Task)
     *
     * @param taskDto сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданную TaskDtoFull
     */
    @PostMapping(value = "/task/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDtoFull> create(@RequestBody TaskDtoFull taskDto) {
        if (taskDto == null)
            throw new BadRequestException("TaskDtoFull == null");

        Task task = dtoFullConverter.toModel(taskDto);
        taskCreateService.create(task);
        TaskDtoFull createdTask = dtoFullConverter.toDto(task);

        return new ResponseEntity<>(createdTask, HttpStatus.OK);
    }

    /**
     * Метод обновления задачи (Task)
     *
     * @param id      идентификатор задачи
     * @param taskDto обновляемая сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает обновленную TaskDtoFull
     */
    @PutMapping(value = "/task/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDtoFull> update(@PathVariable Long id, @RequestBody TaskDtoFull taskDto) {
        if (taskDto == null)
            throw new BadRequestException("TaskDtoFull == null");

        if (!id.equals(taskDto.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        Task task = dtoFullConverter.toModel(taskDto);
        taskRefreshService.refresh(task);
        TaskDtoFull updatedTask = dtoFullConverter.toDto(task);

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    /**
     * Метод удаления задачи (Task)
     *
     * @param id идентификатор задачи
     * @return возвращает статус ответа
     */
    @DeleteMapping(value = "/task/{id}/delete")
    public ResponseEntity<TaskDtoFull> delete(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Task task = taskGetService.get(id);
        taskRemoveService.remove(task);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Метод обновления одного поля задачи (Task)
     *
     * @param id       идентификатор задачи
     * @param oneValue объект, содержащий идентификатор задачи, имя обновляемого поля и новое значение поля
     * @return возвращает UpdateOneValue
     */
    @PutMapping(value = "/task/{id}/updatefield", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateOneValue> updateOneField(@PathVariable Long id, @RequestBody UpdateOneValue oneValue) {
        if (oneValue == null)
            throw new BadRequestException("Значение обновляемого поля отсутствует!");

        if (oneValue.getFieldName().equals("id"))
            throw new OperationIsNotPossibleException("Обновление поля: " + oneValue.getFieldName() + " невозможно!");

        if (!id.equals(oneValue.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        updateOneFieldService.updateOneField(oneValue);

        return new ResponseEntity<>(oneValue, HttpStatus.OK);
    }
}