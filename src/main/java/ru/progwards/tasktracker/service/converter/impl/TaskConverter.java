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

/**
 * конвертер valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskConverter implements Converter<TaskEntity, Task> {

    /**
     * @param entity сущность, полученная из БД
     * @return value object - объект бизнес логики
     */
    @Override
    public Task toVo(TaskEntity entity) {
        if (entity == null)
            return null;
        else
            return new Task(
                    entity.getId(),
                    entity.getCode(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getType(),
                    entity.getPriority(),
                    entity.getProject_id(),
                    entity.getAuthor(),
                    entity.getExecutor(),
                    ZonedDateTime.ofInstant(Instant.ofEpochSecond(entity.getCreated()), ZoneId.of("Europe/Moscow")),
                    checkUpdatedTaskNotNull(entity.getUpdated()),
                    entity.getStatus(),
                    checkDurationTaskNotNull(entity.getEstimation()),
                    checkDurationTaskNotNull(entity.getTimeSpent()),
                    checkDurationTaskNotNull(entity.getTimeLeft()),
                    entity.getRelatedTasks(),
                    entity.getAttachments(),
                    entity.getWorkLogs()
            );
    }

    /**
     * @param duration секунды, могут быть пустыми и значение
     * @return продолжительность или пусто
     */
    private Duration checkDurationTaskNotNull(Long duration) {
        return duration != null ? Duration.ofSeconds(duration) : null;
    }

    /**
     * @param updated секунды, могут быть пустыми и значение
     * @return дату-время или пусто
     */
    private ZonedDateTime checkUpdatedTaskNotNull(Long updated) {
        return updated != null ? ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(updated), ZoneId.of("Europe/Moscow")) : null;
    }

    /**
     * @param valueObject value object - объект бизнес логики
     * @return сущность для БД
     */
    @Override
    public TaskEntity toEntity(Task valueObject) {
        if (valueObject == null)
            return null;
        else {
            if (valueObject.getCreated() == null)
                valueObject.setCreated(ZonedDateTime.now());

            return new TaskEntity(
                    valueObject.getId(),
                    valueObject.getCode(),
                    valueObject.getName(),
                    valueObject.getDescription(),
                    valueObject.getType(),
                    valueObject.getPriority(),
                    valueObject.getProject_id(),
                    valueObject.getAuthor(),
                    valueObject.getExecutor(),
                    valueObject.getCreated().toEpochSecond(),
                    checkUpdatedEntityNotNull(valueObject.getUpdated()),
                    valueObject.getStatus(),
                    checkDurationEntityNotNull(valueObject.getEstimation()),
                    checkDurationEntityNotNull(valueObject.getTimeSpent()),
                    checkDurationEntityNotNull(valueObject.getTimeLeft()),
                    valueObject.getRelatedTasks(),
                    valueObject.getAttachments(),
                    valueObject.getWorkLogs(),
                    false
            );
        }
    }

    /**
     * @param duration продолжительность
     * @return продолжительность в секундах или пусто
     */
    private Long checkDurationEntityNotNull(Duration duration) {
        return duration != null ? duration.toSeconds() : null;
    }

    /**
     * @param time дата-время
     * @return дата-время в секундах или пусто
     */
    private Long checkUpdatedEntityNotNull(ZonedDateTime time) {
        return time != null ? time.toEpochSecond() : null;
    }
}
