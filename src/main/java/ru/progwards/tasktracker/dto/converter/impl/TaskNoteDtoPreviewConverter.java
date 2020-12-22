package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskNoteDtoFull;
import ru.progwards.tasktracker.model.TaskNote;

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
