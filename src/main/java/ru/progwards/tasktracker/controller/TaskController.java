package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.exception.TaskNotFoundException;
import ru.progwards.tasktracker.service.facade.impl.*;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.Collection;

@RestController
@RequestMapping("/rest/task/")
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

    @GetMapping("get/{id}")
    public ResponseEntity<Task> getTask(@PathVariable("id") Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Task task = taskGetService.get(id);

        if (task == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("get")
    public ResponseEntity<Collection<Task>> getAllTasks() {
        Collection<Task> tasks = taskGetListService.getList();

        if (tasks == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Task> addTask(@RequestBody Task task){
        if (task == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        taskCreateService.create(task);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<Task> updateTask(@RequestBody Task task){
        if (task == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        taskRefreshService.refresh(task);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping()

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Task task = taskGetService.get(id);

        if (task != null)
            taskRemoveService.remove(task);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}