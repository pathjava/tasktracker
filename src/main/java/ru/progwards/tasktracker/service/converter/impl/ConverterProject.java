package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.Project;

@Component("converter")
public class ConverterProject implements Converter<ProjectEntity, Project> {

    @Override
    public Project toVo(ProjectEntity entity) {
        return new Project(entity.getId(), entity.getName(), entity.getDescription(), entity.getManagerUserId());
    }

    @Override
    public ProjectEntity toEntity(Project valueObject) {
        return new ProjectEntity(valueObject.getId(), valueObject.getName(), valueObject.getDescription(), valueObject.getManagerUserId());
    }
}
