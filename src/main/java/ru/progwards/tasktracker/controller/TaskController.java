package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.impl.TaskDtoConverter;
import ru.progwards.tasktracker.controller.dto.TaskDto;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.TaskNotFoundException;
import ru.progwards.tasktracker.controller.exception.TaskNotExistException;
import ru.progwards.tasktracker.controller.exception.TasksNotFoundException;
import ru.progwards.tasktracker.service.facade.impl.*;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/project/{project_id}")
public class TaskController {

    private TaskGetService taskGetService;
    private TaskGetListService taskGetListService;
    private TaskRemoveService taskRemoveService;
    private TaskCreateService taskCreateService;
    private TaskRefreshService taskRefreshService;
    private TaskDtoConverter dtoConverter;

    @Autowired
    public void setTaskService(
            TaskGetService taskGetService,
            TaskGetListService taskGetListService,
            TaskRemoveService taskRemoveService,
            TaskCreateService taskCreateService,
            TaskRefreshService taskRefreshService,
            TaskDtoConverter dtoConverter
    ) {
        this.taskGetService = taskGetService;
        this.taskGetListService = taskGetListService;
        this.taskRemoveService = taskRemoveService;
        this.taskCreateService = taskCreateService;
        this.taskRefreshService = taskRefreshService;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long task_id) {
        if (task_id == null)
            throw new BadRequestException("Id: " + task_id + " не задан или задан неверно!");

        TaskDto task = dtoConverter.toDto(taskGetService.get(task_id));

        if (task == null)
            throw new TaskNotFoundException("Задача с id: " + task_id + " не найдена!");

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<Collection<TaskDto>> getAllTasks(@PathVariable Long project_id) {
        if (project_id == null)
            throw new BadRequestException("Id: " + project_id + " не задан или задан неверно!");

        Collection<TaskDto> tasks = getAllTasksByProjectId(project_id);

        if (tasks == null)
            throw new TasksNotFoundException();

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    private Collection<TaskDto> getAllTasksByProjectId(Long project_id) {
        Collection<TaskDto> tasks = taskGetListService.getList().stream()
                .filter(task -> task.getProject().getId().equals(project_id))
                .map(task -> dtoConverter.toDto(task))
                .collect(Collectors.toList());

        return tasks.size() == 0 ? null : tasks;
    }

    @PostMapping("/tasks/create")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        //TODO сделать проверку существования задачи по id
        if (task == null)
            throw new TaskNotExistException();

        taskCreateService.create(task);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/tasks/{task_id}/update")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        if (task == null)
            throw new TaskNotExistException();

        taskRefreshService.refresh(task);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{task_id}/delete")
    public ResponseEntity<Task> deleteTask(@PathVariable Long task_id) {
        if (task_id == null)
            throw new BadRequestException("Id: " + task_id + " не задан или задан неверно!");

        Task task = taskGetService.get(task_id);

        if (task != null)
            taskRemoveService.remove(task);
        else
            throw new TaskNotFoundException("Задача с id: " + task_id + " не найдена!");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}