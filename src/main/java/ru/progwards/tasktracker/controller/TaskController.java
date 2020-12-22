package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskDtoFull;
import ru.progwards.tasktracker.dto.TaskDtoPreview;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.UpdateOneValue;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с задачами
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping(value = "/rest",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {

    private final @NonNull GetService<Long, Task> getService;
    private final @NonNull RemoveService<Task> removeService;
    private final @NonNull CreateService<Task> createService;
    private final @NonNull RefreshService<Task> refreshService;
    private final @NonNull Converter<Task, TaskDtoPreview> dtoPreviewConverter;
    private final @NonNull Converter<Task, TaskDtoFull> dtoFullConverter;
    private final @NonNull GetService<String, Task> byCodeGetService;
    @Qualifier("taskUpdateOneFieldService")
    private final @NonNull UpdateOneFieldService<Task> oneFieldService;
    private final @NonNull GetListByProjectService<Long, Task> listByProjectService;

    /**
     * Метод получения коллекции задач по идентификатору проекта
     *
     * @param id идентификатор проекта
     * @return коллекция превью задач
     */
    @GetMapping(value = "/project/{id}/tasks")
    public ResponseEntity<Collection<TaskDtoPreview>> getListTasks(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Collection<TaskDtoPreview> tasks = listByProjectService.getListByProjectId(id).stream()
                .map(dtoPreviewConverter::toDto)
                .collect(Collectors.toUnmodifiableList());

        if (tasks.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список задач пустой!");

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * Метод создания задачи
     *
     * @param taskDtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданную задачу
     */
    @PostMapping(value = "/task/create")
    public ResponseEntity<TaskDtoFull> createTask(@RequestBody TaskDtoFull taskDtoFull) {
        if (taskDtoFull == null)
            throw new BadRequestException("Пустой объект!");

        Task task = dtoFullConverter.toModel(taskDtoFull);
        createService.create(task);
        TaskDtoFull createdTask = dtoFullConverter.toDto(task);

        //TODO - перед добавлением проверять, есть ли уже в БД такая задача, но id генерируется в entity - подумать

        return new ResponseEntity<>(createdTask, HttpStatus.OK);
    }

    /**
     * Метод обновления задачи
     *
     * @param id идентификатор задачи
     * @param taskDtoFull обновляемая сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает обновленную задачу
     */
    @PutMapping(value = "/task/{id}/update")
    public ResponseEntity<TaskDtoFull> updateTask(@PathVariable Long id, @RequestBody TaskDtoFull taskDtoFull) {
        if (taskDtoFull == null)
            throw new BadRequestException("Пустой объект!");

        if (!id.equals(taskDtoFull.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        Task task = dtoFullConverter.toModel(taskDtoFull);
        refreshService.refresh(task);
        TaskDtoFull updatedTask = dtoFullConverter.toDto(task);

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    /**
     * Метод удаления задачи
     *
     * @param id идентификатор задачи
     * @return возвращает статус ответа
     */
    @DeleteMapping(value = "/task/{id}/delete")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Task task = getService.get(id);
        if (task != null)
            removeService.remove(task);
        else
            throw new NotFoundException("Задача с id: " + id + " не найдена!");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Метод поиска задачи по текстовому коду
     *
     * @param code текстовый идентификатор (код) задачи, создаваемый на основе префикса проекта
     * @return возвращает найденную задачу
     */
    @GetMapping(value = "/task/{code}/getbycode")
    public ResponseEntity<TaskDtoFull> getByCodeTask(@PathVariable String code) {
        if (code == null)
            throw new BadRequestException("Code не задан или задан неверно!");

        TaskDtoFull task = dtoFullConverter.toDto(byCodeGetService.get(code));

        if (task == null) //TODO - пустая задача или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Задача с code: " + code + " не найдена!");
        //TODO - может вместо исключения возвращать статус HttpStatus.NO_CONTENT ?

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Метод обновления одного поля задачи
     *
     * @param id идентификатор задачи
     * @param oneValue объект, содержащий идентификатор задачи, имя обновляемого поля и новое значение поля
     * @return статус
     */
    @PutMapping(value = "/task/{id}/updatefield")
    public ResponseEntity<UpdateOneValue> updateOneField(
            @PathVariable Long id, @RequestBody UpdateOneValue oneValue
    ) {
        if (oneValue == null)
            throw new BadRequestException("Значение обновляемого поля отсутствует!");

        if (!id.equals(oneValue.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        oneFieldService.updateOneField(oneValue);

        return new ResponseEntity<>(oneValue, HttpStatus.OK);
    }
}