package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.service.facade.impl.TaskGetListService;
import ru.progwards.tasktracker.service.facade.impl.TaskGetService;
import ru.progwards.tasktracker.service.facade.impl.TaskRemoveService;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.Collection;

@RestController
@RequestMapping("/rest/task/")
public class TaskController {

    private TaskGetService taskGetService;
    private TaskGetListService taskGetListService;
    private TaskRemoveService taskRemoveService;

    @Autowired
    public void setTaskGetService(
            TaskGetService taskGetService,
            TaskGetListService taskGetListService,
            TaskRemoveService taskRemoveService
    ) {
        this.taskGetService = taskGetService;
        this.taskGetListService = taskGetListService;
        this.taskRemoveService = taskRemoveService;
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


    @GetMapping("/rest/task/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable("id") Long id){
        if (id == null)
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Task task = taskGetService.get(id);

        if (task != null)
            taskRemoveService.remove(task);
        else
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}