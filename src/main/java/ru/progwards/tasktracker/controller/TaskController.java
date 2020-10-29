package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDtoFull;
import ru.progwards.tasktracker.controller.dto.TaskDtoPreview;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotExistException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    private GetService<Long, Task> taskGetService;
    private GetListService<Task> taskGetListService;
    private RemoveService<Task> taskRemoveService;
    private CreateService<Task> taskCreateService;
    private RefreshService<Task> taskRefreshService;
    private Converter<Task, TaskDtoPreview> dtoPreviewConverter;
    private Converter<Task, TaskDtoFull> dtoFullConverter;
    private GetService<String, Task> byCodeGetService;

    @Autowired
    public void setTaskGetService(
            GetService<Long, Task> taskGetService,
            GetListService<Task> taskGetListService,
            RemoveService<Task> taskRemoveService,
            CreateService<Task> taskCreateService,
            RefreshService<Task> taskRefreshService,
            Converter<Task, TaskDtoPreview> dtoPreviewConverter,
            Converter<Task, TaskDtoFull> dtoFullConverter,
            GetService<String, Task> byCodeGetService
    ) {
        this.taskGetService = taskGetService;
        this.taskGetListService = taskGetListService;
        this.taskRemoveService = taskRemoveService;
        this.taskCreateService = taskCreateService;
        this.taskRefreshService = taskRefreshService;
        this.dtoPreviewConverter = dtoPreviewConverter;
        this.dtoFullConverter = dtoFullConverter;
        this.byCodeGetService = byCodeGetService;
    }

    @GetMapping("/rest/project/{project_id}/tasks/{task_id}")
    public ResponseEntity<TaskDtoPreview> getTask(@PathVariable Long task_id) {
        if (task_id == null)
            throw new BadRequestException("Id: " + task_id + " не задан или задан неверно!");

        TaskDtoPreview task = dtoPreviewConverter.toDto(taskGetService.get(task_id));

        if (task == null)
            throw new NotFoundException("Задача с id: " + task_id + " не найдена!");

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/tt/browse/{code}/{id}")
    public ResponseEntity<TaskDtoFull> getFullTask(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Code не задан или задан неверно!");

        TaskDtoFull task = dtoFullConverter.toDto(taskGetService.get(id));

        if (task == null)
            throw new NotFoundException("Задача с code: " + id + " не найдена!");

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/rest/project/{project_id}/tasks")
    public ResponseEntity<Collection<TaskDtoPreview>> getAllTasks(@PathVariable Long project_id) {
        if (project_id == null)
            throw new BadRequestException("Id: " + project_id + " не задан или задан неверно!");

        Collection<TaskDtoPreview> tasks = getAllTasksByProjectId(project_id);

        if (tasks == null)
            throw new NotFoundException("Задача не найдена!");

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    private Collection<TaskDtoPreview> getAllTasksByProjectId(Long project_id) {
        Collection<TaskDtoPreview> tasks = taskGetListService.getList().stream()
                .filter(task -> task.getProject_id().equals(project_id))
                .map(task -> dtoPreviewConverter.toDto(task))
                .collect(Collectors.toList());

        return tasks.size() == 0 ? null : tasks;
    }

    @PostMapping("/rest/project/{project_id}/tasks/create")
    public ResponseEntity<TaskDtoFull> addTask(@RequestBody TaskDtoFull task) {
        //TODO сделать проверку существования задачи по id
        if (task == null)
            throw new NotExistException("Задача не существует!");

        taskCreateService.create(dtoFullConverter.toModel(task));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/rest/project/{project_id}/tasks/{task_id}/update")
    public ResponseEntity<TaskDtoFull> updateTask(@RequestBody TaskDtoFull task) {
        if (task == null)
            throw new NotExistException("Задача не существует!");

        taskRefreshService.refresh(dtoFullConverter.toModel(task));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/rest/project/{project_id}/tasks/{task_id}/delete")
    public ResponseEntity<Task> deleteTask(@PathVariable Long task_id) {
        if (task_id == null)
            throw new BadRequestException("Id: " + task_id + " не задан или задан неверно!");

        Task task = taskGetService.get(task_id);

        if (task != null)
            taskRemoveService.remove(task);
        else
            throw new NotFoundException("Задача с id: " + task_id + " не найдена!");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tt/browse/{code}")
    public ResponseEntity<TaskDtoFull> getTaskByCode(@PathVariable String code) {
        if (code == null)
            throw new BadRequestException("Code не задан или задан неверно!");

        TaskDtoFull task = dtoFullConverter.toDto(byCodeGetService.get(code));

        if (task == null)
            throw new NotFoundException("Задача с code: " + code + " не найдена!");

        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}