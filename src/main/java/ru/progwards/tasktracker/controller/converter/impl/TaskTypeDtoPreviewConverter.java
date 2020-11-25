package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskTypeDtoPreview;
import ru.progwards.tasktracker.service.vo.TaskType;

/**
 * @author Oleg Kiselev
 */
@Component
public class TaskTypeDtoPreviewConverter implements Converter<TaskType, TaskTypeDtoPreview> {

    @Override
    public TaskType toModel(TaskTypeDtoPreview dto) {
        return null;
    }

    @Override
    public TaskTypeDtoPreview toDto(TaskType model) {
        return null;
    }
}
