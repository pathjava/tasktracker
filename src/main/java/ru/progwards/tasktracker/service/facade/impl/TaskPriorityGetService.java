package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.TaskPriorityEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.TaskPriorityConverter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.TaskPriority;

@Component
public class TaskPriorityGetService implements GetService<Long, TaskPriority> {

    private final TaskPriorityEntityRepository repository;
    private final TaskPriorityConverter converter;

    public TaskPriorityGetService(TaskPriorityEntityRepository repository, TaskPriorityConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public TaskPriority get(Long id) {
        return converter.toVo(repository.get(id));
    }
}
