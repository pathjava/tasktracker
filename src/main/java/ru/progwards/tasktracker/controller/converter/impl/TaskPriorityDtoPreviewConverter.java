package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskPriorityDtoPreview;
import ru.progwards.tasktracker.service.vo.TaskPriority;

@Component
public class TaskPriorityDtoPreviewConverter implements Converter<TaskPriority, TaskPriorityDtoPreview> {
    @Override
    public TaskPriority toModel(TaskPriorityDtoPreview dto) {
        return null;
    }

    @Override
    public TaskPriorityDtoPreview toDto(TaskPriority model) {
        return null;
    }
}
