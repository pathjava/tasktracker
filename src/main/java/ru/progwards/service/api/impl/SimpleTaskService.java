package ru.progwards.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.progwards.repository.dao.impl.TaskRepository;
import ru.progwards.repository.entity.TaskEntity;
import ru.progwards.service.api.ChangeTaskService;
import ru.progwards.service.converter.Converter;
import ru.progwards.util.types.Priority;
import ru.progwards.service.vo.Task;
import ru.progwards.service.vo.User;

public abstract class SimpleTaskService implements ChangeTaskService {

    private TaskRepository taskRepository;
    private Converter<TaskEntity, Task> converter;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setConverter(Converter<TaskEntity, Task> converter) {
        this.converter = converter;
    }

    public void createTask(Task task) {
        taskRepository.create(converter.convertFrom(task));
    }

    public void setUser(User user, Task task) {

    }

    public void setPriority(Priority priority, Task task) {

    }
}
