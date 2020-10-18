package ru.progwards.tasktracker.controller.converter.impl;

import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDto;
import ru.progwards.tasktracker.service.vo.Task;

public class TaskInPreview implements Converter<Task, TaskDto> {

    @Override
    public Task toModel(TaskDto dto) {
        return null;
    }

    @Override
    public TaskDto toDto(Task model) {
        return null;
    }
}
