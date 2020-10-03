package ru.progwards.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.service.converter.Converter;
import ru.progwards.service.vo.Task;
import ru.progwards.repository.entity.TaskEntity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class ConverterTask implements Converter<TaskEntity, Task> {

    @Override
    public Task convertTo(TaskEntity task) {
        return new Task(task.getId(), task.getName(), task.getDescription(),
                task.getType(), task.getPriority(), task.getAuthorUserId(), task.getExecutorUserId(),
                ZonedDateTime.ofInstant(Instant.ofEpochSecond(task.getCreated()), ZoneId.of("Europe/Moscow")),
                ZonedDateTime.ofInstant(Instant.ofEpochSecond(task.getUpdated()), ZoneId.of("Europe/Moscow")),
                task.getStoryPoint(), task.getProjectId(), task.getStrCode(),
                task.getWfStatus(), task.getVersion(), task.getPlanDuration(),
                task.getSpentDuration(), task.getLeftDuration());
    }

    @Override
    public TaskEntity convertFrom(Task taskModel) {
        return new TaskEntity(taskModel.getId(), taskModel.getName(), taskModel.getDescription(),
                taskModel.getType(), taskModel.getPriority(), taskModel.getAuthorUserId(),
                taskModel.getExecutorUserId(), taskModel.getCreated().toEpochSecond(),
                taskModel.getUpdated().toEpochSecond(), taskModel.getStoryPoint(),
                taskModel.getProjectId(), taskModel.getStrCode(), taskModel.getWfStatus(),
                taskModel.getVersion(), taskModel.getPlanDuration(),
                taskModel.getSpentDuration(), taskModel.getLeftDuration());
    }
}
