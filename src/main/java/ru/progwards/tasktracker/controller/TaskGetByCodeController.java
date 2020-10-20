package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.TaskNotFoundException;
import ru.progwards.tasktracker.service.facade.impl.TaskByCodeGetService;
import ru.progwards.tasktracker.service.vo.Task;

@RestController
public class TaskGetByCodeController {

    private TaskByCodeGetService byCodeGetService;

    @Autowired
    public void setEntityRepository(TaskByCodeGetService byCodeGetService) {
        this.byCodeGetService = byCodeGetService;
    }

    @GetMapping("/tt/browse/{code}")
    public ResponseEntity<Task> getTaskByCode(@PathVariable String code) {
        if (code == null)
            throw new BadRequestException("Code не задан или задан неверно!");

        Task task = byCodeGetService.get(code);

        if (task == null)
            throw new TaskNotFoundException("Задача с code: " + code + " не найдена!");

        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
