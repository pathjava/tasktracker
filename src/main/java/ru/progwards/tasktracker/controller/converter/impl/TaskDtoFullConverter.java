package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDtoFull;
import ru.progwards.tasktracker.service.vo.Task;

@Component
public class TaskDtoFullConverter implements Converter<Task, TaskDtoFull> {

    @Override
    public Task toModel(TaskDtoFull dto) {
        if (dto == null)
            return null;
        else
            return new Task(
                    dto.getId(),
                    dto.getCode(),
                    dto.getName(),
                    dto.getDescription(),
                    dto.getType(),
                    dto.getPriority(),
                    dto.getProject_id(),
                    dto.getAuthor(),
                    dto.getExecutor(),
                    dto.getCreated(),
                    dto.getUpdated(),
                    dto.getStatus(),
                    dto.getEstimation(),
                    dto.getTimeSpent(),
                    dto.getTimeLeft(),
                    dto.getRelatedTasks(),
                    dto.getAttachments(),
                    dto.getWorkLogs()
            );
    }

    @Override
    public TaskDtoFull toDto(Task task) {
        if (task == null)
            return null;
        else
            return new TaskDtoFull(
                    task.getId(),
                    task.getCode(),
                    task.getName(),
                    task.getDescription(),
                    task.getType(),
                    task.getPriority(),
                    task.getProject_id(),
                    task.getAuthor(),
                    task.getExecutor(),
                    task.getCreated(),
                    task.getUpdated(),
                    task.getStatus(),
                    task.getEstimation(),
                    task.getTimeSpent(),
                    task.getTimeLeft(),
                    task.getRelatedTasks(),
                    task.getAttachments(),
                    task.getWorkLogs()
            );
    }
}
