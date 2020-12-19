package ru.progwards.tasktracker.repository.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetListByProjectService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.TaskType;
import ru.progwards.tasktracker.service.vo.User;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Конвертер ProjectEntity <-> Project
 * @author Pavel Khovaylo
 */
@Component
public class ProjectConverter implements Converter<ProjectEntity, Project> {
    /**
     * сервис по поиску пользователя
     */
    @Autowired
    private GetService<Long, User> userGetService;

    @Autowired
    private GetListByProjectService<Long, TaskType> taskTypeGetListByProjectService;

    /**
     * метод по преобразованию сущности репозитория в бизнес-модель
     * @param entity сущность репозитория, которую необходимо преобразовать
     * @return бизнес-модель
     */
    @Override
    public Project toVo(ProjectEntity entity) {
//        if (entity == null)
            return null;

//        return new Project(entity.getId(), entity.getName(), entity.getDescription(),
//                entity.getPrefix(), userGetService.get(entity.getOwnerId()), getZDTCreated(entity.getCreated()),
//                (List<TaskType>) taskTypeGetListByProjectService.getListByProjectId(entity.getId()),
//                entity.getLastTaskCode());
    }

    /**
     * метод по преобразованию бизнес-модели в сущность репозитория
     * @param valueObject бизнес-модель, которую необходимо преобразовать
     * @return сущность репозитория
     */
    @Override
    public ProjectEntity toEntity(Project valueObject) {
//        if (valueObject == null)
            return null;

//        return new ProjectEntity(valueObject.getId(), valueObject.getName(), valueObject.getDescription(),
//                valueObject.getPrefix(), valueObject.getOwner().getId(), getLongCreated(valueObject.getCreated()),
//                valueObject.getLastTaskCode());
    }

    /**
     * метод переводит объект ZonedDateTime в миллисекунды
     * @param created объект ZonedDateTime, котрый необходимо преобразовать
     * @return количество миллисекунд
     */
    private Long getLongCreated(ZonedDateTime created) {
        return created.toEpochSecond() * 1000;
    }

    /**
     * метод, позволяющий из миллисекунд получить объект ZonedDateTime
     * @param milli количество миллисекунд
     * @return получившийся объект ZonedDateTime
     */
    private ZonedDateTime getZDTCreated(Long milli) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.of("Europe/Moscow"));
    }
}