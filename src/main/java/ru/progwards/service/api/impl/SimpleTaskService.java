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

    private TaskRepository repo;
    private Converter<TaskEntity, Task> converter;

    @Autowired
    public void setRepo(TaskRepository repo) {
        this.repo = repo;
    }

    @Autowired
    public void setConverter(Converter<TaskEntity, Task> converter) {
        this.converter = converter;
    }

    public void createTask(Task taskModel) {
        repo.create(converter.convertFrom(taskModel));
    }

    public void setUser(User userModel, Task taskModel) {

    }

    public void setPriority(Priority priority, Task taskModel) {

    }
}
