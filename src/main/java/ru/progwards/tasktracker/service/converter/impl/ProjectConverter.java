package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.Task;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectConverter implements Converter<ProjectEntity, Project> {

    @Autowired
    private GetListService<Task> taskGetListService;

    @Override
    public Project toVo(ProjectEntity entity) {
        if (entity == null)
            return null;

        return new Project(entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrefix(),
                null,
                getZDTCreated(entity.getCreated()),
                null,
                getListTasks(entity.getId()),
                entity.getLastTaskCode()
        );
    }

    @Override
    public ProjectEntity toEntity(Project valueObject) {
        if (valueObject == null)
            return null;

        return new ProjectEntity(
                valueObject.getId(),
                valueObject.getName(),
                valueObject.getDescription(),
                valueObject.getPrefix(),
                null,
                getLongCreated(valueObject.getCreated()),
                null,
                valueObject.getLastTaskCode()
        );
    }

    private Long getLongCreated(ZonedDateTime created) {
        return created.toEpochSecond() * 1000;
    }

    private ZonedDateTime getZDTCreated(Long milli) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.of("Europe/Moscow"));
    }

    private List<Task> getListTasks(Long id) {
        return taskGetListService.getList().stream().filter(e -> e.getId().equals(id)).collect(Collectors.toList());
    }
}
