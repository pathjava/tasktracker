package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.progwards.tasktracker.service.facade.impl.TaskGetListService;
import ru.progwards.tasktracker.service.facade.impl.TaskGetService;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.Collection;

@RestController
public class TaskController {

    private TaskGetService taskGetService;
    private TaskGetListService taskGetListService;

    @Autowired
    public void setTaskGetService(TaskGetService taskGetService) {
        this.taskGetService = taskGetService;
    }

    @Autowired
    public void setTaskGetListService(TaskGetListService taskGetListService) {
        this.taskGetListService = taskGetListService;
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
}
