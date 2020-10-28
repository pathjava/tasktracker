package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.ProjectConverter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.Project;

@Component
public class ProjectCreateService implements CreateService<Project> {

    private final ProjectEntityRepository repository;
    private final ProjectConverter converter;

    public ProjectCreateService(ProjectEntityRepository repository, ProjectConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public void create(Project model) {
        repository.create(converter.toEntity(model));
    }
}