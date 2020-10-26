package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.TaskPriorityEntityRepository;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.TaskPriority;

@Component
public class TaskPriorityRemoveService implements RemoveService<TaskPriority> {

    private final TaskPriorityEntityRepository repository;

    public TaskPriorityRemoveService(TaskPriorityEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void remove(TaskPriority model) {
        repository.delete(model.getId());
    }
}
