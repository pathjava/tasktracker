package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.TaskPriorityEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.TaskPriorityConverter;
import ru.progwards.tasktracker.service.facade.RefreshService;
import ru.progwards.tasktracker.service.vo.TaskPriority;

@Component
public class TaskPriorityRefreshService implements RefreshService<TaskPriority> {

    private final TaskPriorityEntityRepository repository;
    private final TaskPriorityConverter converter;

    public TaskPriorityRefreshService(TaskPriorityEntityRepository repository, TaskPriorityConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public void refresh(TaskPriority model) {
        repository.update(converter.toEntity(model));
    }
}
