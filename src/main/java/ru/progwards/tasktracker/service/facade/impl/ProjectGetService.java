package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.ConverterProject;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Project;

@Component
public class ProjectGetService implements GetService<Long, Project> {

    private final ProjectEntityRepository repository;
    private final ConverterProject converter;

    public ProjectGetService(ProjectEntityRepository repository, ConverterProject converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public Project get(Long id) {
        return converter.toVo(repository.get(id));
    }
}