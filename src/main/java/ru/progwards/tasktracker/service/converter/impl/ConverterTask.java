package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.repository.entity.TaskEntity;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class ConverterTask implements Converter<TaskEntity, Task> {

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
                    taskEntity.getProject(),
                    taskEntity.getAuthor(),
                    taskEntity.getExecutor(),
                    ZonedDateTime.ofInstant(Instant.ofEpochSecond(taskEntity.getCreated()), ZoneId.of("Europe/Moscow")),
                    ZonedDateTime.ofInstant(Instant.ofEpochSecond(taskEntity.getUpdated()), ZoneId.of("Europe/Moscow")),
                    taskEntity.getStatus(),
                    Duration.ofSeconds(taskEntity.getEstimation()),
                    Duration.ofSeconds(taskEntity.getTimeSpent()),
                    Duration.ofSeconds(taskEntity.getTimeLeft()),
                    taskEntity.getRelatedTasks(),
                    taskEntity.getAttachments(),
                    taskEntity.getWorkLogs()
            );
    }

    @Override
    public TaskEntity toEntity(Task task) {
        if (task == null)
            return null;
        else
            return new TaskEntity(
                    task.getId(),
                    task.getCode(),
                    task.getName(),
                    task.getDescription(),
                    task.getType(),
                    task.getPriority(),
                    task.getProject(),
                    task.getAuthor(),
                    task.getExecutor(),
                    task.getCreated().toEpochSecond(),
                    task.getUpdated().toEpochSecond(),
                    task.getStatus(),
                    task.getEstimation().toSeconds(),
                    task.getTimeSpent().toSeconds(),
                    task.getTimeLeft().toSeconds(),
                    task.getRelatedTasks(),
                    task.getAttachments(),
                    task.getWorkLogs()
            );
    }
}
