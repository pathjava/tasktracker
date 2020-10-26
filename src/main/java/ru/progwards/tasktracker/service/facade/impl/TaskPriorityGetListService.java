package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.TaskPriorityEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.TaskPriorityConverter;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.vo.TaskPriority;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TaskPriorityGetListService implements GetListService<TaskPriority> {

    private final TaskPriorityEntityRepository repository;
    private final TaskPriorityConverter converter;

    public TaskPriorityGetListService(TaskPriorityEntityRepository repository, TaskPriorityConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public Collection<TaskPriority> getList() {
        return repository.get().stream().map(e -> converter.toVo(e)).collect(Collectors.toList());
    }
}
