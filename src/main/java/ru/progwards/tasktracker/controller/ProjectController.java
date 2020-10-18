package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.exceptions.NotFoundProjectException;
import ru.progwards.tasktracker.controller.exceptions.NullProjectException;
import ru.progwards.tasktracker.service.facade.impl.*;
import ru.progwards.tasktracker.service.vo.Project;

import java.util.Collection;

@RestController
public class ProjectController {

    @Autowired
    private ProjectGetListService projectGetListService;

    @Autowired
    private ProjectGetService projectGetService;

    @Autowired
    private ProjectCreateService projectCreateService;

    @Autowired
    private ProjectRefreshService projectRefreshService;

    @Autowired
    private ProjectOneFieldSetService projectOneFieldSetService;

    @Autowired
    private ProjectRemoveService projectRemoveService;

    @GetMapping("/rest/project/list")
    public ResponseEntity<Collection<Project>> get() {
        return new ResponseEntity<>(projectGetListService.getList(), HttpStatus.OK);
    }

    @GetMapping("/rest/project/{id}")
    public Project get(@PathVariable("id") Long id) {
        Project project = projectGetService.get(id);
        if (project == null)
            throw new NotFoundProjectException("Not found a project with id=" + id);

        return project;
    }

    @PostMapping("/rest/project/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {
        Project project = projectGetService.get(id);
        if (project == null)
            throw new NotFoundProjectException("Not found a project with id=" + id);

        projectRemoveService.remove(project);
    }

    @PostMapping("/rest/project/create")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody Project project) {
        if (project == null)
            throw new NullProjectException("Project is null");

        projectCreateService.create(project);
    }
}
