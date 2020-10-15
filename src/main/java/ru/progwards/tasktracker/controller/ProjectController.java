package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;

import java.util.Collection;

@RestController
public class ProjectController {

    @Autowired
    private ProjectEntityRepository repository;

    @GetMapping("/rest/project/list")
    public ResponseEntity<Collection<ProjectEntity>> getProjects() {
        return new ResponseEntity<>(repository.get(), HttpStatus.OK);
    }

    @GetMapping("/rest/project/{id}")
    public @ResponseBody ProjectEntity getTask(@PathVariable("id") Long id) {
        return repository.get(id);
    }

    @DeleteMapping("/rest/project/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        repository.delete(id);
    }
}
