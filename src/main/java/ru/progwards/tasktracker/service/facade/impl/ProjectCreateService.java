package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.ConverterProject;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.Project;

@Component
public class ProjectCreateService implements CreateService<Project> {

    @Autowired
    private ProjectEntityRepository repository;

    @Autowired
    private ConverterProject converter;

    @Override
    public void create(Project model) {
        repository.create(converter.toEntity(model));
    }
}