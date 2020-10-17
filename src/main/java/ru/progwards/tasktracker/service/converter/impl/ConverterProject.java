package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.Project;

@Component("converter")
public class ConverterProject implements Converter<ProjectEntity, Project> {

    @Override
    public Project toVo(ProjectEntity entity) {
        if (entity == null)
            return null;

        return new Project(entity.getId(), entity.getName(), entity.getDescription(), null, null,
                null, null, null);
    }

    @Override
    public ProjectEntity toEntity(Project valueObject) {
        if (valueObject == null)
            return null;

        return new ProjectEntity(valueObject.getId(), valueObject.getName(), valueObject.getDescription(),
                valueObject.getOwner().getId());
    }
}
