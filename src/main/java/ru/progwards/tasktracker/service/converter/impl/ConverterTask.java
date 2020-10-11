package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.repository.entity.TaskEntity;

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
            return new Task(taskEntity.getId(), taskEntity.getName(), taskEntity.getDescription(),
                    taskEntity.getType(), taskEntity.getPriority(), taskEntity.getAuthorUserId(), taskEntity.getExecutorUserId(),
                    ZonedDateTime.ofInstant(Instant.ofEpochSecond(taskEntity.getCreated()), ZoneId.of("Europe/Moscow")),
                    ZonedDateTime.ofInstant(Instant.ofEpochSecond(taskEntity.getUpdated()), ZoneId.of("Europe/Moscow")),
                    taskEntity.getStoryPoint(), taskEntity.getProjectId(), taskEntity.getStrCode(),
                    taskEntity.getWfStatus(), taskEntity.getVersion(), taskEntity.getPlanDuration(),
                    taskEntity.getSpentDuration(), taskEntity.getLeftDuration());
    }

    @Override
    public TaskEntity toEntity(Task task) {
        if (task == null)
            return null;
        else
            return new TaskEntity(task.getId(), task.getName(), task.getDescription(),
                    task.getType(), task.getPriority(), task.getAuthorUserId(),
                    task.getExecutorUserId(), task.getCreated().toEpochSecond(),
                    task.getUpdated().toEpochSecond(), task.getStoryPoint(),
                    task.getProjectId(), task.getStrCode(), task.getWfStatus(),
                    task.getVersion(), task.getPlanDuration(),
                    task.getSpentDuration(), task.getLeftDuration());
    }
}
