package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.repository.entity.TaskEntity;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

@Component
public class TaskConverter implements Converter<TaskEntity, Task> {

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

    private Duration checkThatDurationTaskNotNull(Long duration) {
        return duration != null ? Duration.ofSeconds(duration) : null;
    }

    private ZonedDateTime checkThatUpdatedTaskNotNull(Long updated) {
        return updated != null ? ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(updated), ZoneId.of("Europe/Moscow")) : null;
    }

    @Override
    public TaskEntity toEntity(Task task) {
        if (task == null)
            return null;
        else
            return new TaskEntity(
                    generateId(task.getId()),
                    task.getCode(),
                    task.getName(),
                    task.getDescription(),
                    task.getType(),
                    task.getPriority(),
                    task.getProject_id(),
                    task.getAuthor(),
                    task.getExecutor(),
                    setTimeTaskCreated(task.getCreated()),
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

    private Long setTimeTaskCreated(ZonedDateTime created){
        return created == null ? ZonedDateTime.now().toEpochSecond() : created.toEpochSecond();
    }

    //TODO - only for test generate Id
    private Long generateId(Long id){
        return id == null ? new Random().nextLong() : id;
    }

    private Long checkThatDurationTaskEntityNotNull(Duration duration) {
        return duration != null ? duration.toSeconds() : null;
    }

    private Long checkThatUpdatedTaskEntityNotNull(ZonedDateTime time) {
        return time != null ? time.toEpochSecond() : null;
    }
}
