package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.impl.TaskGetListService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConverterProject implements Converter<ProjectEntity, Project> {

    private final TaskGetListService taskGetListService;

    public ConverterProject(TaskGetListService taskGetListService) {
        this.taskGetListService = taskGetListService;
    }

    @Override
    public Project toVo(ProjectEntity entity) {
        if (entity == null)
            return null;

        return new Project(entity.getId(), entity.getName(), entity.getDescription(),
                new User(entity.getId()), getZDTCreated(entity.getCreated()), new WorkFlow(entity.getWorkFlowId()),
                getListTasks(entity.getId()), entity.getLastTaskCode());
    }

    @Override
    public ProjectEntity toEntity(Project valueObject) {
        if (valueObject == null)
            return null;

        return new ProjectEntity(valueObject.getId(), valueObject.getName(), valueObject.getDescription(),
                valueObject.getPrefix(), valueObject.getOwner().getId(), getLongCreated(valueObject.getCreated()),
                valueObject.getWorkFlow().getId(), valueObject.getLastTaskCode());
    }

    private Long getLongCreated(ZonedDateTime created) {
        return created.toEpochSecond() * 1000;
    }

    private ZonedDateTime getZDTCreated(Long milli) {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.of("Europe/Moscow"));
        return zdt;
    }

    private List<Task> getListTasks(Long id) {
        return taskGetListService.getList().stream().filter(e -> e.getId() == id).collect(Collectors.toList());
    }
}