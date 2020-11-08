package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDtoFull;
import ru.progwards.tasktracker.controller.dto.TaskDtoPreview;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.Task;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * контроллер для работы с задачами
 *
 * @author Oleg Kiselev
 */
@RestController
public class TaskController {

    private GetService<Long, Task> taskGetService;
    private RemoveService<Task> taskRemoveService;
    private CreateService<Task> taskCreateService;
    private RefreshService<Task> taskRefreshService;
    private Converter<Task, TaskDtoPreview> dtoPreviewConverter;
    private Converter<Task, TaskDtoFull> dtoFullConverter;
    private GetService<String, Task> byCodeGetService;
    private GetListByProjectService<Long, Task> listByProjectService;

    @Autowired
    public void setTaskGetService(
            GetService<Long, Task> taskGetService,
            RemoveService<Task> taskRemoveService,
            CreateService<Task> taskCreateService,
            RefreshService<Task> taskRefreshService,
            Converter<Task, TaskDtoPreview> dtoPreviewConverter,
            Converter<Task, TaskDtoFull> dtoFullConverter,
            GetService<String, Task> byCodeGetService,
            GetListByProjectService<Long, Task> listByProjectService
    ) {
        this.taskGetService = taskGetService;
        this.taskRemoveService = taskRemoveService;
        this.taskCreateService = taskCreateService;
        this.taskRefreshService = taskRefreshService;
        this.dtoPreviewConverter = dtoPreviewConverter;
        this.dtoFullConverter = dtoFullConverter;
        this.byCodeGetService = byCodeGetService;
        this.listByProjectService = listByProjectService;
    }

    /**
     * выборка списка задач по идентификатору проекта
     *
     * @param project_id идентификатор проекта
     * @return коллекция превью задач
     */
    @GetMapping("/rest/project/{project_id}/tasks")
    public ResponseEntity<Collection<TaskDtoPreview>> getListTasks(@PathVariable Long project_id) {
        if (project_id == null)
            throw new BadRequestException("Id: " + project_id + " не задан или задан неверно!");

        Collection<TaskDtoPreview> tasks = listByProjectService.getListByProjectId(project_id).stream()
                .map(task -> dtoPreviewConverter.toDto(task))
                .collect(Collectors.toList());

        if (tasks.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список задач пустой!");

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * метод создания задачи
     *
     * @param taskDtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданную задачу
     */
    @PostMapping("/rest/task/create")
    public ResponseEntity<TaskDtoFull> createTask(@RequestBody TaskDtoFull taskDtoFull) {
        if (taskDtoFull == null)
            throw new BadRequestException("Пустой объект!");

        Task task = dtoFullConverter.toModel(taskDtoFull);
        taskCreateService.create(task);
        TaskDtoFull createdTask = dtoFullConverter.toDto(task);

        //TODO - перед добавлением проверять, есть ли уже в БД такая задача, но id генерируется в entity - подумать

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * метод обновления задачи
     *
     * @param task_id идентификатор задачи
     * @param taskDtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает обновленную задачу
     */
    @PutMapping("/rest/project/{project_id}/tasks/{task_id}/update")
    public ResponseEntity<TaskDtoFull> updateTask(@PathVariable Long task_id, @RequestBody TaskDtoFull taskDtoFull) {
        if (taskDtoFull == null)
            throw new BadRequestException("Задача не существует!");

        if (!task_id.equals(taskDtoFull.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        Task task = dtoFullConverter.toModel(taskDtoFull);
        taskRefreshService.refresh(task);
        TaskDtoFull updatedTask = dtoFullConverter.toDto(task);

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    /**
     * метод для удаления задачи
     *
     * @param task_id идентификатор задачи
     * @return возвращает статус ответа
     */
    @DeleteMapping("/rest/project/{project_id}/tasks/{task_id}/delete")
    public ResponseEntity<Task> deleteTask(@PathVariable Long task_id) {
        if (task_id == null)
            throw new BadRequestException("Id: " + task_id + " не задан или задан неверно!");

        Task task = taskGetService.get(task_id);
        if (task != null)//TODO - при удалении задачи вызывать метод удаления связанных задач
            taskRemoveService.remove(task);
        else
            throw new NotFoundException("Задача с id: " + task_id + " не найдена!");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * метод поиска задачи по текстовому коду
     *
     * @param code текстовый идентификатор (код) задачи, создаваемый на основе префикса проекта
     * @return возвращает найденную задачу
     */
    @GetMapping("/rest/task/{code}/getbycode")
    public ResponseEntity<TaskDtoFull> getByCodeTask(@PathVariable String code) {
        if (code == null)
            throw new BadRequestException("Code не задан или задан неверно!");

        TaskDtoFull task = dtoFullConverter.toDto(byCodeGetService.get(code));

        if (task == null) //TODO - пустая задача или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Задача с code: " + code + " не найдена!");

        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}