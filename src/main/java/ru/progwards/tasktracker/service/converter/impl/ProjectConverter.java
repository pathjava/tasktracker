package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, позволяющий преобразовывать сущности репозитория в бизнес-модель и обратно
 * @author Pavel Khovaylo
 */
@Component
public class ProjectConverter implements Converter<ProjectEntity, Project> {

    /**
     * сервис со списком всех задач
     */
    @Autowired
    private GetListService<Task> taskGetListService;

    /**
     * сервис по поиску пользователя
     */
    @Autowired
    private GetService<Long, User> userGetService;

    @Autowired
    private GetService<Long, WorkFlow> workFlowGetService;

    /**
     * метод по преобразованию сущности репозитория в бизнес-модель
     * @param entity сущность репозитория, которую необходимо преобразовать
     * @return бизнес-модель
     */
    @Override
    public Project toVo(ProjectEntity entity) {
        if (entity == null)
            return null;

        return new Project(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getPrefix(), userGetService.get(entity.getOwnerId()),
                getZDTCreated(entity.getCreated()), workFlowGetService.get(entity.getWorkFlowId()),
                getListTasks(entity.getId()), entity.getLastTaskCode());
        //TODO переговорить с командой о создании метода в объекте GetListService, при помощи
        // которого получать задачи конктетного проекта
    }

    /**
     * метод по преобразованию бизнес-модели в сущность репозитория
     * @param valueObject бизнес-модель, которую необходимо преобразовать
     * @return сущность репозитория
     */
    @Override
    public ProjectEntity toEntity(Project valueObject) {
        if (valueObject == null)
            return null;

        return new ProjectEntity(valueObject.getId(), valueObject.getName(), valueObject.getDescription(),
                valueObject.getPrefix(), valueObject.getOwnerId(), getLongCreated(valueObject.getCreated()),
                valueObject.getWorkFlowId(), valueObject.getLastTaskCode());
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

    /**
     * метод, позволяющий получить список задач конкретного проекта
     * @param projectId идентификатор нужного проекта
     * @return список задач данного проета
     */
    private List<Task> getListTasks(Long projectId) {
        return taskGetListService.getList().stream().
                filter(e -> e.getProject_id().equals(projectId)).collect(Collectors.toList());
    }
}