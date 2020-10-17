package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.exception.IdBadRequestException;
import ru.progwards.tasktracker.controller.exception.TaskByIdNotFoundException;
import ru.progwards.tasktracker.controller.exception.TaskNotExistException;
import ru.progwards.tasktracker.controller.exception.TasksNotFoundException;
import ru.progwards.tasktracker.service.facade.impl.*;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/project/{project_id}")
public class TaskController {

    private TaskGetService taskGetService;
    private TaskGetListService taskGetListService;
    private TaskRemoveService taskRemoveService;
    private TaskCreateService taskCreateService;
    private TaskRefreshService taskRefreshService;

    @Autowired
    public void setTaskService(
            TaskGetService taskGetService,
            TaskGetListService taskGetListService,
            TaskRemoveService taskRemoveService,
            TaskCreateService taskCreateService,
            TaskRefreshService taskRefreshService
    ) {
        this.taskGetService = taskGetService;
        this.taskGetListService = taskGetListService;
        this.taskRemoveService = taskRemoveService;
        this.taskCreateService = taskCreateService;
        this.taskRefreshService = taskRefreshService;
    }

    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<Task> getTask(@PathVariable Long task_id) {
        if (task_id == null)
            throw new IdBadRequestException(task_id);

        Task task = taskGetService.get(task_id);

        if (task == null)
            throw new TaskByIdNotFoundException(task_id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<Collection<Task>> getAllTasks(@PathVariable Long project_id) {
        if (project_id == null)
            throw new IdBadRequestException(project_id);

//        Collection<Task> tasks = taskGetListService.getList().stream()
//        .filter(task -> task.getProject().getId().equals(project_id)).collect(Collectors.toList());

//        Collection<Task> tasks = taskGetListService.getList().stream()
//                .filter(task -> Optional.of(task.getProject().getId().equals(project_id)).orElse(null))
//                .collect(Collectors.toList());

//        Collection<Task> tasks = Optional.of(
//                taskGetListService.getList().stream()
//                        .filter(task -> task.getProject().getId().equals(project_id))
//                        .collect(Collectors.toList())
//        ).orElse(null);

        Collection<Task> tasks = Optional.ofNullable(taskGetListService.getList())
                .map(task -> task.stream()
                        .filter(t -> t.getProject().getId().equals(project_id))
                        .collect(Collectors.toList()))
                .orElse(null);

        if (tasks == null)
            throw new TasksNotFoundException();

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        //TODO сделать проверку существования задачи по id
        if (task == null)
            throw new TaskNotExistException();

        taskCreateService.create(task);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        if (task == null)
            throw new TaskNotExistException();

        taskRefreshService.refresh(task);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") Long id) {
        if (id == null)
            throw new IdBadRequestException(id);

        Task task = taskGetService.get(id);

        if (task != null)
            taskRemoveService.remove(task);
        else
            throw new TaskByIdNotFoundException(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}