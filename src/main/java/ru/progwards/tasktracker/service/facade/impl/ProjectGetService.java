package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.ConverterProject;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Project;

public class ProjectGetService implements GetService<Long, Project> {

    @Autowired
    private ProjectEntityRepository repository;

    @Autowired
    private ConverterProject converter;

    @Override
    public Project get(Long id) {
        return converter.toVo(repository.get(id));
    }
}