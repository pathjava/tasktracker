package ru.progwards.tasktracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.repository.dao.impl.TaskPriorityEntityRepository;
import ru.progwards.tasktracker.repository.entity.TaskPriorityEntity;

import java.util.Collection;

@RestController
@RequestMapping("/rest/taskpriority/")
public class TaskPriorityController {

    private final TaskPriorityEntityRepository repository;

    public TaskPriorityController(TaskPriorityEntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("list")
    public ResponseEntity<Collection<TaskPriorityEntity>> get() {
        return new ResponseEntity<>(repository.get(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskPriorityEntity> get(@PathVariable("id") Long id) {
        TaskPriorityEntity entity = repository.get(id);
        if (entity == null)
            throw new NotFoundException("Not found a taskpriority with id=" + id);

        return new ResponseEntity<>(repository.get(id), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<TaskPriorityEntity> create(@RequestBody TaskPriorityEntity entity) {
        if (entity == null)
            throw new BadRequestException("TaskPriority is null");

        repository.create(entity);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody TaskPriorityEntity entity) {
        if (entity == null)
            throw new BadRequestException("TaskPriority is null");

        entity.setId(id);
        repository.update(entity);
    }

    @PostMapping("{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        TaskPriorityEntity entity = repository.get(id);
        if (entity == null)
            throw new NotFoundException("Not found a taskpriority with id=" + id);

        repository.delete(id);
    }
}