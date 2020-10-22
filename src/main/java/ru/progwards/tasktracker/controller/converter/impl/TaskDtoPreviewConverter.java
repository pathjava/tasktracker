package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDtoPreview;
import ru.progwards.tasktracker.service.vo.Task;

@Component
public class TaskDtoPreviewConverter implements Converter<Task, TaskDtoPreview> {

    @Override
    public Task toModel(TaskDtoPreview dto) {
        return null;
    }

    @Override
    public TaskDtoPreview toDto(Task task) {
        if (task == null)
            return null;
        else
            return new TaskDtoPreview(
                    task.getId(),
                    task.getCode(),
                    task.getName()
            );
    }
}
