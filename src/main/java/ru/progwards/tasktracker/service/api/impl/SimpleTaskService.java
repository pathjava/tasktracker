package ru.progwards.tasktracker.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.progwards.tasktracker.repository.dao.impl.TaskRepository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.api.ChangeTaskService;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.Priority;

public abstract class SimpleTaskService implements ChangeTaskService {

    private TaskRepository repo;
    private Converter<TaskEntity, Task> converter;

    public void createTask(Task model) {
        //repo.save(converter.convertFrom(model));
    }

    public void setUser(User model, Task model2) {
        //
    }

    public void setPriority(Priority priority, Task model) {

    }
}
