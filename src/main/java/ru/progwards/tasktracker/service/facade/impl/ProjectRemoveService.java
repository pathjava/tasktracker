package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.ConverterProject;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.Project;

@Component
public class ProjectRemoveService implements RemoveService<Project> {

    @Autowired
    private ProjectEntityRepository repository;

    @Autowired
    private ConverterProject converter;

    @Override
    public void remove(Project model) {
        repository.delete(model.getId());
    }
}