package ru.progwards.tasktracker.repository.deprecated.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskPriorityEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.model.TaskPriority;

@Component
public class TaskPriorityConverter implements Converter<TaskPriorityEntity, TaskPriority> {
    @Override
    public TaskPriority toVo(TaskPriorityEntity entity) {
//        return new TaskPriority(entity.getId(), entity.getName(), entity.getValue());
        return null;
    }

    @Override
    public TaskPriorityEntity toEntity(TaskPriority valueObject) {
//        return new TaskPriorityEntity(valueObject.getId(), valueObject.getName(), valueObject.getValue());
        return null;
    }
}