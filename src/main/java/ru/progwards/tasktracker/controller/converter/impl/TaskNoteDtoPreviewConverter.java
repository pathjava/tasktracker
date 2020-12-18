package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskNoteDtoFull;
import ru.progwards.tasktracker.service.vo.TaskNote;

@Component
public class TaskNoteDtoPreviewConverter implements Converter<TaskNote, TaskNoteDtoFull> {
    @Override
    public TaskNote toModel(TaskNoteDtoFull dto) {
        return null;
    }

    @Override
    public TaskNoteDtoFull toDto(TaskNote model) {
        return null;
    }
}
