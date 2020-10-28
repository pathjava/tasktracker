package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.ProjectConverter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Project;

@Component
public class ProjectGetService implements GetService<Long, Project> {

    private final ProjectEntityRepository repository;
    private final ProjectConverter converter;

    public ProjectGetService(ProjectEntityRepository repository, ProjectConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public Project get(Long id) {
        return converter.toVo(repository.get(id));
    }
}