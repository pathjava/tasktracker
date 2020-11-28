package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskNoteDtoPreview;
import ru.progwards.tasktracker.service.vo.TaskNote;

@Component
public class TaskNoteDtoPreviewConverter implements Converter<TaskNote, TaskNoteDtoPreview> {
    @Override
    public TaskNote toModel(TaskNoteDtoPreview dto) {
        return null;
    }

    @Override
    public TaskNoteDtoPreview toDto(TaskNote model) {
        return null;
    }
}
