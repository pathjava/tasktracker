package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.ConverterProject;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.Project;

@Component
public class ProjectRemoveService implements RemoveService<Project> {

    private final ProjectEntityRepository repository;

    private final ConverterProject converter;

    public ProjectRemoveService(ProjectEntityRepository repository, ConverterProject converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public void remove(Project model) {
        repository.delete(model.getId());
    }
}