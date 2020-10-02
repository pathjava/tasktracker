package service.converter.impl;

import org.springframework.stereotype.Component;
import service.converter.Converter;
import service.vo.TaskModel;
import repository.entity.Task;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class ConverterTask implements Converter<Task, TaskModel> {

    @Override
    public TaskModel convertTo(Task task) {
        return new TaskModel(task.getId(), task.getName(), task.getDescription(),
                task.getType(), task.getPriority(), task.getAuthorUserId(), task.getExecutorUserId(),
                ZonedDateTime.ofInstant(Instant.ofEpochSecond(task.getCreated()), ZoneId.of("Europe/Moscow")),
                ZonedDateTime.ofInstant(Instant.ofEpochSecond(task.getUpdated()), ZoneId.of("Europe/Moscow")),
                task.getStoryPoint(), task.getProjectId(), task.getStrCode(),
                task.getWfStatus(), task.getVersion(), task.getPlanDuration(),
                task.getSpentDuration(), task.getLeftDuration());
    }

    @Override
    public Task convertFrom(TaskModel taskModel) {
        return new Task(taskModel.getId(), taskModel.getName(), taskModel.getDescription(),
                taskModel.getType(), taskModel.getPriority(), taskModel.getAuthorUserId(),
                taskModel.getExecutorUserId(), taskModel.getCreated().toEpochSecond(),
                taskModel.getUpdated().toEpochSecond(), taskModel.getStoryPoint(),
                taskModel.getProjectId(), taskModel.getStrCode(), taskModel.getWfStatus(),
                taskModel.getVersion(), taskModel.getPlanDuration(),
                taskModel.getSpentDuration(), taskModel.getLeftDuration());
    }
}
