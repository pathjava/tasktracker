package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RefreshService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.Task;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * конвертер между value object и entity
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskConverter implements Converter<TaskEntity, Task> {

    private GetService<Long, Project> projectGetService;
    private RefreshService<Project> refreshService;

    @Autowired
    public void setProjectGetService(
            GetService<Long, Project> projectGetService,
            RefreshService<Project> refreshService
    ) {
        this.projectGetService = projectGetService;
        this.refreshService = refreshService;
    }

    /**
     * @param taskEntity сущность, полученная из БД
     * @return value object - объект бизнес логики
     */
    @Override
    public Task toVo(TaskEntity taskEntity) {
        if (taskEntity == null)
            return null;
        else
            return new Task(
                    taskEntity.getId(),
                    taskEntity.getCode(),
                    taskEntity.getName(),
                    taskEntity.getDescription(),
                    taskEntity.getType(),
                    taskEntity.getPriority(),
                    taskEntity.getProject_id(),
                    taskEntity.getAuthor(),
                    taskEntity.getExecutor(),
                    ZonedDateTime.ofInstant(Instant.ofEpochSecond(taskEntity.getCreated()), ZoneId.of("Europe/Moscow")),
                    checkThatUpdatedTaskNotNull(taskEntity.getUpdated()),
                    taskEntity.getStatus(),
                    checkThatDurationTaskNotNull(taskEntity.getEstimation()),
                    checkThatDurationTaskNotNull(taskEntity.getTimeSpent()),
                    checkThatDurationTaskNotNull(taskEntity.getTimeLeft()),
                    taskEntity.getRelatedTasks(),
                    taskEntity.getAttachments(),
                    taskEntity.getWorkLogs()
            );
    }

    /**
     * @param duration секунды, могут быть пустыми и значение
     * @return продолжительность или пусто
     */
    private Duration checkThatDurationTaskNotNull(Long duration) {
        return duration != null ? Duration.ofSeconds(duration) : null;
    }

    /**
     * @param updated секунды, могут быть пустыми и значение
     * @return дату-время или пусто
     */
    private ZonedDateTime checkThatUpdatedTaskNotNull(Long updated) {
        return updated != null ? ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(updated), ZoneId.of("Europe/Moscow")) : null;
    }

    /**
     * @param task value object - объект бизнес логики
     * @return сущность для БД
     */
    @Override
    public TaskEntity toEntity(Task task) {
        if (task == null)
            return null;
        else {
            if (task.getCreated() == null)
                task.setCreated(ZonedDateTime.now());
            if (task.getCode() == null)
                task.setCode(generateTaskCode(task.getProject_id()));

            return new TaskEntity(
                    checkNotNull(task),
                    task.getCode(),
                    task.getName(),
                    task.getDescription(),
                    task.getType(),
                    task.getPriority(),
                    task.getProject_id(),
                    task.getAuthor(),
                    task.getExecutor(),
                    task.getCreated().toEpochSecond(),
                    checkThatUpdatedTaskEntityNotNull(task.getUpdated()),
                    task.getStatus(),
                    checkThatDurationTaskEntityNotNull(task.getEstimation()),
                    checkThatDurationTaskEntityNotNull(task.getTimeSpent()),
                    checkThatDurationTaskEntityNotNull(task.getTimeLeft()),
                    task.getRelatedTasks(),
                    task.getAttachments(),
                    task.getWorkLogs(),
                    false
            );
        }
    }

    private Long checkNotNull(Task task) {
        return task.getId() == null ? null : task.getId();
    }

    /**
     * @param project_id идентификатор проекта, к которому принадлежит задача
     * @return код задачи в формате "NGR-1"
     */
    private String generateTaskCode(Long project_id) {
        Project project = projectGetService.get(project_id);
        Long lastTaskCode = project.getLastTaskCode();
        String taskCode = project.getPrefix() + "-" + lastTaskCode;
        project.setLastTaskCode(lastTaskCode + 1);
        refreshService.refresh(project);
        return taskCode;
    }

    /**
     * @param duration продолжительность
     * @return продолжительность в секундах или пусто
     */
    private Long checkThatDurationTaskEntityNotNull(Duration duration) {
        return duration != null ? duration.toSeconds() : null;
    }

    /**
     * @param time дата-время
     * @return дата-время в секундах или пусто
     */
    private Long checkThatUpdatedTaskEntityNotNull(ZonedDateTime time) {
        return time != null ? time.toEpochSecond() : null;
    }
}
