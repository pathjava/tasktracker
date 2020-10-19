package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDto;
import ru.progwards.tasktracker.service.facade.impl.TaskGetService;
import ru.progwards.tasktracker.service.vo.Task;

@Component
public class TaskDtoConverter implements Converter<Task, TaskDto> {

    private TaskGetService getService;

    @Autowired
    public void setGetService(TaskGetService getService) {
        this.getService = getService;
    }

    @Override
    public Task toModel(TaskDto dto) {
        if (dto == null)
            return null;
        else {
            Task tempTask = getService.get(dto.getId());
            return new Task(
                    dto.getId(),
                    dto.getCode(),
                    dto.getName(),
                    tempTask.getDescription(),
                    tempTask.getType(),
                    tempTask.getPriority(),
                    tempTask.getProject(),
                    tempTask.getAuthor(),
                    tempTask.getExecutor(),
                    tempTask.getCreated(),
                    tempTask.getUpdated(),
                    tempTask.getStatus(),
                    tempTask.getEstimation(),
                    tempTask.getTimeSpent(),
                    tempTask.getTimeLeft(),
                    tempTask.getRelatedTasks(),
                    tempTask.getAttachments(),
                    tempTask.getWorkLogs()
            );
        }
    }

    @Override
    public TaskDto toDto(Task task) {
        if (task == null)
            return null;
        else
            return new TaskDto(
                    task.getId(),
                    task.getCode(),
                    task.getName()
            );
    }
}
