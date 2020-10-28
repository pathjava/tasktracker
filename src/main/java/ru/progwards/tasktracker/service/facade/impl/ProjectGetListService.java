package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.ProjectConverter;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.vo.Project;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ProjectGetListService implements GetListService<Project> {

    private final ProjectEntityRepository repository;
    private final ProjectConverter converter;

    public ProjectGetListService(ProjectEntityRepository repository, ProjectConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public Collection<Project> getList() {
        return repository.get().stream().map(e -> converter.toVo(e)).collect(Collectors.toList());
    }
}