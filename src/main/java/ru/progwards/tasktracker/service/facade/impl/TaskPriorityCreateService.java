package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.TaskPriorityEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.TaskPriorityConverter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.TaskPriority;

@Component
public class TaskPriorityCreateService implements CreateService<TaskPriority> {

    private final TaskPriorityEntityRepository repository;
    private final TaskPriorityConverter converter;

    public TaskPriorityCreateService(TaskPriorityEntityRepository repository, TaskPriorityConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public void create(TaskPriority model) {
        repository.create(converter.toEntity(model));
    }
}
